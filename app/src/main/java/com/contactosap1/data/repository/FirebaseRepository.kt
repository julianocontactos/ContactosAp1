package com.contactosap1.data.repository

import com.contactosap1.data.model.Arquivo
import com.contactosap1.data.model.Empresa
import com.contactosap1.data.model.Equipamento
import com.contactosap1.data.model.Painel
import com.contactosap1.data.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    // Coleções do Firestore
    private val empresasCollection = firestore.collection("empresas")
    private val usuariosCollection = firestore.collection("usuarios")
    private val paineisCollection = firestore.collection("paineis")
    private val equipamentosCollection = firestore.collection("equipamentos")
    private val arquivosCollection = firestore.collection("arquivos")

    // Métodos de Autenticação
    suspend fun login(email: String, password: String): String {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user?.uid ?: throw Exception("Falha na autenticação")
        } catch (e: Exception) {
            throw e
        }
    }

    fun logout() {
        auth.signOut()
    }
    
    suspend fun forgotPassword(email: String) {
        try {
            auth.sendPasswordResetEmail(email).await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun cadastrarUsuario(email: String, password: String, nome: String, idEmpresa: String): String {
        return try {
            // Criar o usuário na autenticação
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("Falha ao criar usuário")
            
            // Criar o documento do usuário no Firestore
            val usuario = Usuario(
                id = uid,
                nome = nome,
                email = email,
                tipo = "user",
                idEmpresa = idEmpresa,
                empresasPermitidas = listOf(idEmpresa)
            )
            
            usuariosCollection.document(uid).set(usuario).await()
            uid
        } catch (e: Exception) {
            throw e
        }
    }
    
    suspend fun getCurrentUser(): Usuario? {
        val uid = auth.currentUser?.uid ?: return null
        return try {
            val document = usuariosCollection.document(uid).get().await()
            document.toObject(Usuario::class.java)
        } catch (e: Exception) {
            null
        }
    }

    // Métodos de Empresa
    suspend fun criarEmpresa(empresa: Empresa): String {
        val id = UUID.randomUUID().toString()
        val novaEmpresa = empresa.copy(id = id)
        empresasCollection.document(id).set(novaEmpresa).await()
        return id
    }
    
    suspend fun getEmpresas(): List<Empresa> {
        return try {
            val snapshot = empresasCollection.get().await()
            snapshot.toObjects(Empresa::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Métodos de Painel
    suspend fun criarPainel(painel: Painel): String {
        val id = UUID.randomUUID().toString()
        val novoPainel = painel.copy(id = id)
        paineisCollection.document(id).set(novoPainel).await()
        return id
    }

    // Métodos de Equipamento
    suspend fun criarEquipamento(equipamento: Equipamento): String {
        val id = UUID.randomUUID().toString()
        val novoEquipamento = equipamento.copy(id = id)
        equipamentosCollection.document(id).set(novoEquipamento).await()
        return id
    }

    // Métodos de Arquivo
    suspend fun criarArquivo(arquivo: Arquivo): String {
        val id = UUID.randomUUID().toString()
        val novoArquivo = arquivo.copy(id = id)
        arquivosCollection.document(id).set(novoArquivo).await()
        return id
    }
} 