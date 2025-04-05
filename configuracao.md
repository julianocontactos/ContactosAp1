# Sistema Mobile de Controle de Equipamentos para Android v10

## 🔰 Objetivo Geral
Desenvolver um sistema mobile voltado para tablets Android v10 que permita o gerenciamento eficiente de empresas, painéis, equipamentos e seus respectivos arquivos. O sistema será multiempresa e multiusuário, com controle de permissões administrativas, login seguro, personalização visual e integração com QR Code.

## 🧱 Arquitetura Geral do Sistema

### 🌐 Plataforma:
- Frontend: Aplicativo mobile (Flutter recomendado)
- Backend: REST API (Node.js, Laravel, Django ou similar)
- Banco de Dados: PostgreSQL / MySQL / Firebase
- Armazenamento de arquivos: Servidor HTTP/S3/FTP para documentos
- Autenticação: JWT com recuperação de senha via e-mail
- QR Code: Leitura com câmera e geração via API

### 👥 Usuários e Permissões
**Tipos de usuários:**
- Administrador: acesso completo (criar, editar, excluir, visualizar)
- Usuário comum: acesso somente leitura (sem editar, criar ou excluir)

**Permissões:**
- Cada usuário terá permissões baseadas em seu tipo e nas empresas às quais está vinculado.

### 🔐 Autenticação e Segurança
- Login com usuário (e-mail) e senha hash
- Recuperação de senha por e-mail
- Proteção por token JWT para sessões autenticadas
- Autorização baseada em nível de acesso e empresa

### 🖌 Personalização de Layout
- Cores (primária, secundária)
- Fonte do sistema
- Logos e imagem de fundo
- Ícone do APK
- Estilo de botões

Cada personalização pode ser global ou associada a uma empresa específica.

## 📂 Cadastro e Estrutura Hierárquica

### 1. empresa
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INTEGER PK | Identificador |
| nome | TEXT | Nome da empresa |
| cnpj | TEXT | CNPJ |
| email | TEXT | Contato da empresa |
| observacoes | TEXT | Observações gerais |

### 2. usuario
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INTEGER PK | Identificador |
| nome | TEXT | Nome completo |
| email | TEXT | Login e recuperação |
| senha | TEXT | Senha criptografada |
| foto | TEXT | Caminho para imagem de perfil |
| observacoes | TEXT | Observações sobre o usuário |
| tipo | TEXT | 'adm' ou 'user' |
| id_empresa | INTEGER FK | Empresa principal vinculada |

### 3. usuario_empresa
Permite um usuário acessar múltiplas empresas.

| Campo | Tipo | Descrição |
|-------|------|-----------|
| usuario_id | INTEGER FK | ID do usuário |
| empresa_id | INTEGER FK | ID da empresa permitida |

### 4. paineis
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INTEGER PK | Identificador |
| nome | TEXT | Nome do painel |
| local | TEXT | Localização física |
| qrcode | TEXT | Código gerado/associado |
| id_empresa | INTEGER FK | Empresa proprietária |

### 5. equipamentos
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INTEGER PK | Identificador |
| nome | TEXT | Nome do equipamento |
| marca | TEXT | Marca |
| modelo | TEXT | Modelo |
| numero_serie | TEXT | Nº de série |
| foto | TEXT | Caminho da imagem |
| qrcode | TEXT | QR Code do equipamento |
| id_painel | INTEGER FK | Relacionamento com painel |

### 6. arquivos
| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | INTEGER PK | Identificador |
| id_equipamento | INTEGER FK | Relacionamento com equipamento |
| tipo | TEXT | Tipo (pdf, word, excel, imagem, vídeo, etc.) |
| nome | TEXT | Nome do arquivo |
| caminho | TEXT | Caminho ou URL de acesso |

## 🔍 Funcionalidades Principais

### 🔐 Login e Segurança
- Login com e-mail e senha
- Recuperação de senha via e-mail
- Sessão com token seguro
- Controle de permissão por tipo de usuário

### 🏢 Multiempresa
- Um usuário pode ter acesso a várias empresas
- Cada empresa com seus próprios painéis, equipamentos e arquivos

### 🧭 Navegação Hierárquica
- Empresa
  - ↳ Paineis
    - ↳ Equipamentos
      - ↳ Arquivos

### 📱 Pesquisa e QR Code
- Campo de busca para equipamentos e painéis
- Botão de leitura de QR Code com a câmera
- Se QR de painel → mostra cadastro do painel
- Se QR de equipamento → mostra cadastro do equipamento
- Botão Gerar QR Code nos cadastros

### 🧩 Personalização Visual
- Alterar logo, cores, fontes, fundo, ícone do app
- Configuração aplicada por empresa ou global

### 🗂 Tela de Equipamento
- Foto do equipamento
- Campos de identificação (nome, marca, modelo, nº série)
- QR Code do equipamento
- Lista de documentos vinculados
- Abrir PDF, imagens, vídeos, planilhas diretamente
- Botão "Adicionar Arquivo" (admin)

## 📦 Tecnologias Recomendadas
| Componente | Sugestão |
|------------|----------|
| Mobile UI | Flutter |
| Backend | Node.js (Express) / Django / Laravel |
| Auth | Firebase Auth / JWT personalizado |
| Banco de Dados | PostgreSQL / MySQL / Firebase |
| Armazenamento | Amazon S3 / Google Cloud Storage |
| QR Code | qr_flutter / qr_code_scanner |
| E-mail | SendGrid / SMTP personalizado |

## 📌 Telas Previstas
- Login
- Recuperar senha
- Dashboard (empresa/painel/equipamento)
- Cadastro de empresa
- Cadastro de usuário
- Cadastro de painel
- Cadastro de equipamento
- Tela de visualização/edição de arquivos
- Tela de configuração de layout
- Leitor de QR Code 