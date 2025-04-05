package com.contactosap1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.contactosap1.data.repository.FirebaseRepository
import com.contactosap1.ui.screens.LoginScreen
import com.contactosap1.ui.theme.ContactosAp1Theme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val repository = FirebaseRepository()
    private val auth = FirebaseAuth.getInstance()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContactosAp1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent(repository)
                }
            }
        }
    }
}

@Composable
fun AppContent(repository: FirebaseRepository) {
    var isLoggedIn by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    // Verificar se o usuário já está logado
    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        isLoggedIn = currentUser != null
    }
    
    if (isLoggedIn) {
        // Aqui vamos mostrar o conteúdo principal do app quando estiver implementado
        // Por enquanto, apenas mostraremos um texto
        androidx.compose.material3.Text(
            text = "Usuário logado com sucesso!",
            modifier = Modifier.fillMaxSize()
        )
    } else {
        LoginScreen { email, senha ->
            scope.launch {
                try {
                    repository.login(email, senha)
                    isLoggedIn = true
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Erro ao fazer login: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
} 