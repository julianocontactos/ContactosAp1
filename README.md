# ContactosAp1

Sistema Mobile de Controle de Equipamentos para Android v10.

## Descrição

O ContactosAp1 é um sistema mobile para tablets Android v10 que permite o gerenciamento eficiente de empresas, painéis, equipamentos e seus respectivos arquivos. O sistema é multiempresa e multiusuário, com controle de permissões administrativas, login seguro, personalização visual e integração com QR Code.

## Tecnologias Utilizadas

- **Frontend**: Kotlin com Jetpack Compose
- **Backend**: Firebase (Auth, Firestore, Storage)
- **QR Code**: ZXing

## Funcionalidades

- Autenticação de usuários (login/cadastro)
- Gerenciamento de empresas
- Gerenciamento de painéis
- Gerenciamento de equipamentos
- Gerenciamento de arquivos
- Leitura e geração de QR Codes
- Personalização visual

## Configuração do Projeto

### Pré-requisitos

- Android Studio (versão mais recente)
- JDK 11 ou superior
- Dispositivo/Emulador Android com API level 29 (Android 10) ou superior

### Configuração do Firebase

1. Crie um projeto no [Firebase Console](https://console.firebase.google.com/)
2. Adicione um aplicativo Android com o pacote `com.contactosap1`
3. Baixe o arquivo `google-services.json` e coloque-o na pasta `app/`

### Estrutura do Projeto

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/contactosap1/
│   │   │   ├── data/
│   │   │   │   ├── model/        # Classes de modelo
│   │   │   │   └── repository/   # Repositórios (Firebase)
│   │   │   ├── di/               # Injeção de dependência
│   │   │   ├── ui/
│   │   │   │   ├── components/   # Componentes reutilizáveis
│   │   │   │   ├── screens/      # Telas do aplicativo
│   │   │   │   └── theme/        # Tema do aplicativo
│   │   │   ├── utils/            # Utilitários
│   │   │   ├── ContactosApplication.kt
│   │   │   └── MainActivity.kt
│   │   ├── res/                  # Recursos (layouts, strings, etc)
│   │   └── AndroidManifest.xml
│   └── test/                     # Testes
└── build.gradle.kts
```

## Execução

1. Abra o projeto no Android Studio
2. Sincronize o Gradle
3. Execute o aplicativo em um dispositivo ou emulador Android

## Contribuição

Este projeto foi iniciado para atender às necessidades específicas de gerenciamento de equipamentos. Contribuições são bem-vindas através de pull requests.

## Licença

Este projeto está licenciado sob a licença [MIT](LICENSE).
