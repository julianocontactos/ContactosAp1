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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contactosap1.data.model.Empresa
import com.contactosap1.data.repository.FirebaseRepository
import com.contactosap1.ui.theme.ContactosAp1Theme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    repository: FirebaseRepository,
    onRegisterSuccess: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf("") }
    var empresas by remember { mutableStateOf<List<Empresa>>(emptyList()) }
    var empresaSelecionada by remember { mutableStateOf<Empresa?>(null) }
    var expandedDropdown by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        try {
            empresas = repository.getEmpresas()
            if (empresas.isNotEmpty()) {
                empresaSelecionada = empresas.first()
            }
        } catch (e: Exception) {
            erro = "Erro ao carregar empresas: ${e.message}"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cadastro de Usuário") },
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
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = senha,
                onValueChange = { senha = it },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = confirmarSenha,
                onValueChange = { confirmarSenha = it },
                label = { Text("Confirmar Senha") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            ExposedDropdownMenuBox(
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = it },
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    value = empresaSelecionada?.nome ?: "Selecione uma empresa",
                    onValueChange = {},
                    label = { Text("Empresa") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) }
                )
                
                ExposedDropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false }
                ) {
                    empresas.forEach { empresa ->
                        DropdownMenuItem(
                            text = { Text(empresa.nome) },
                            onClick = {
                                empresaSelecionada = empresa
                                expandedDropdown = false
                            }
                        )
                    }
                }
            }
            
            if (erro.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = erro,
                    color = androidx.compose.ui.graphics.Color.Red
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = { 
                    if (validarCampos()) {
                        scope.launch {
                            try {
                                empresaSelecionada?.id?.let { idEmpresa ->
                                    repository.cadastrarUsuario(email, senha, nome, idEmpresa)
                                    onRegisterSuccess()
                                }
                            } catch (e: Exception) {
                                erro = "Erro ao cadastrar: ${e.message}"
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cadastrar")
            }
        }
    }
}

private fun RegisterScreen.validarCampos(): Boolean {
    if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
        erro = "Preencha todos os campos"
        return false
    }
    
    if (senha != confirmarSenha) {
        erro = "As senhas não conferem"
        return false
    }
    
    if (senha.length < 6) {
        erro = "A senha deve ter pelo menos 6 caracteres"
        return false
    }
    
    if (empresaSelecionada == null) {
        erro = "Selecione uma empresa"
        return false
    }
    
    return true
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ContactosAp1Theme {
        // RegisterScreen() // Não é possível preview com repository
    }
} 