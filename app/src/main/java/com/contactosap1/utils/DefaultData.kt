package com.contactosap1.utils

import com.contactosap1.data.model.Empresa
import com.contactosap1.data.repository.FirebaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DefaultData {
    
    fun criarDadosPadrao() {
        val repository = FirebaseRepository()
        
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Verificar se já existem empresas
                val empresas = repository.getEmpresas()
                if (empresas.isEmpty()) {
                    // Criar empresa padrão
                    val empresa = Empresa(
                        nome = "Empresa Demonstração",
                        cnpj = "12.345.678/0001-99",
                        email = "contato@empresademo.com",
                        observacoes = "Empresa criada para demonstração"
                    )
                    repository.criarEmpresa(empresa)
                }
            } catch (e: Exception) {
                // Ignorar erro
            }
        }
    }
} 