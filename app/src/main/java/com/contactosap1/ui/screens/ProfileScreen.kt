package com.contactosap1.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.contactosap1.data.model.Usuario
import com.contactosap1.data.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    repository: FirebaseRepository = FirebaseRepository(),
    onBackClick: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var usuario by remember { mutableStateOf<Usuario?>(null) }
    var carregando by remember { mutableStateOf(true) }
    var erro by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    
    LaunchedEffect(Unit) {
        try {
            usuario = repository.getCurrentUser()
            usuario?.let {
                nome = it.nome
            }
            carregando = false
        } catch (e: Exception) {
            erro = "Erro ao carregar perfil: ${e.message}"
            carregando = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil do Usuário") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (carregando) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (erro.isNotEmpty()) {
                Text(
                    text = erro,
                    color = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else {
                usuario?.let {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Surface(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            color = androidx.compose.ui.graphics.Color.LightGray
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(64.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = "Email: ${it.email}",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        
                        Text(
                            text = "Tipo: ${if (it.tipo == "adm") "Administrador" else "Usuário comum"}",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        OutlinedTextField(
                            value = nome,
                            onValueChange = { nome = it },
                            label = { Text("Nome") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Button(
                            onClick = {
                                // Aqui implementaríamos a atualização do perfil
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Atualizar Perfil")
                        }
                    }
                }
            }
        }
    }
} 