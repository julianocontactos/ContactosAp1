package com.contactosap1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contactosap1.data.repository.FirebaseRepository
import com.contactosap1.ui.theme.ContactosAp1Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    repository: FirebaseRepository,
    onBackClick: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }
    var enviado by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recuperar Senha") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Digite seu e-mail para receber instruções de recuperação de senha",
                modifier = Modifier.padding(vertical = 16.dp)
            )
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            
            if (erro.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = erro,
                    color = androidx.compose.ui.graphics.Color.Red
                )
            }
            
            if (enviado) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Email de recuperação enviado com sucesso!",
                    color = androidx.compose.ui.graphics.Color.Green
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = { 
                    if (email.isNotEmpty()) {
                        scope.launch {
                            try {
                                repository.forgotPassword(email)
                                enviado = true
                                erro = ""
                            } catch (e: Exception) {
                                erro = "Erro ao enviar email: ${e.message}"
                                enviado = false
                            }
                        }
                    } else {
                        erro = "Digite seu email"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    ContactosAp1Theme {
        // ForgotPasswordScreen() // Não é possível preview com repository
    }
} 