# 🏦 Agregador de Investimentos

## 📋 Sobre o Projeto

O **Agregador de Investimentos** é uma aplicação Spring Boot que permite aos usuários gerenciar seus investimentos em ações de forma centralizada. A aplicação oferece funcionalidades para criação de contas, associação de ações, registro de transações e integração com APIs externas para cotação de ativos.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.1**
- **Spring Security** (Autenticação JWT)
- **Spring Data JPA** (Persistência de dados)
- **MySQL** (Banco de dados)
- **OpenFeign** (Cliente HTTP para APIs externas)
- **Swagger/OpenAPI** (Documentação da API)
- **Maven** (Gerenciamento de dependências)

## 🏗️ Arquitetura da Aplicação

### Estrutura de Pacotes

```
src/main/java/brenno/AgredadorInvestimentos/
├── Config/                    # Configurações da aplicação
│   ├── SecurityConfig.java   # Configuração de segurança
│   └── JwtAuthenticationFilter.java # Filtro JWT
├── Controller/               # Camada de apresentação (REST APIs)
│   ├── AuthController.java   # Autenticação e autorização
│   ├── UserController.java   # Gerenciamento de usuários
│   ├── AccountController.java # Gerenciamento de contas
│   ├── StockController.java  # Gerenciamento de ações
│   └── TransactionController.java # Gerenciamento de transações
├── Service/                  # Camada de lógica de negócio
│   ├── UserService.java      # Serviços de usuário
│   ├── AccountService.java   # Serviços de conta
│   ├── StockService.java     # Serviços de ações
│   ├── TransactionService.java # Serviços de transações
│   ├── JwtService.java       # Serviços JWT
│   └── CustomUserDetailsService.java # Detalhes do usuário
├── Entity/                   # Entidades JPA (Modelo de dados)
│   ├── User.java            # Entidade Usuário
│   ├── Account.java         # Entidade Conta
│   ├── Stock.java           # Entidade Ação
│   ├── Transaction.java     # Entidade Transação
│   └── BillingAddress.java  # Entidade Endereço
├── Repository/              # Camada de acesso a dados
│   ├── UserRepository.java  # Repositório de usuários
│   ├── AccountRepository.java # Repositório de contas
│   ├── StockRepository.java # Repositório de ações
│   └── TransactionRepository.java # Repositório de transações
└── Client/                  # Clientes para APIs externas
    └── BrapiClient.java     # Cliente para API Brapi
```

## 🔐 Sistema de Segurança

### Autenticação JWT

A aplicação utiliza **JSON Web Tokens (JWT)** para autenticação stateless:

- **Registro**: Usuários podem se registrar fornecendo username, email e senha
- **Login**: Autenticação via email/senha retorna um token JWT
- **Autorização**: Token JWT é enviado no header `Authorization: Bearer <token>`
- **Criptografia**: Senhas são criptografadas usando BCrypt

### Endpoints Públicos vs Protegidos

#### ✅ **Endpoints Públicos** (não requerem autenticação):
- `POST /v1/users` - Criar novo usuário
- `POST /v1/auth/login` - Fazer login
- `/swagger-ui/**` - Documentação da API

#### 🔒 **Endpoints Protegidos** (requerem token JWT):
- Todos os outros endpoints da aplicação

## 📊 Modelo de Dados

### Entidades Principais

#### 👤 **User (Usuário)**
- `userId` (UUID) - Identificador único
- `username` - Nome do usuário
- `email` - Email (usado para login)
- `password` - Senha criptografada
- `creationTimestamp` - Data de criação
- `updateTimestamp` - Data de atualização
- `accounts` - Lista de contas do usuário

#### 🏦 **Account (Conta)**
- `accountId` (UUID) - Identificador único
- `description` - Descrição da conta
- `user` - Usuário proprietário
- `billingAddress` - Endereço de cobrança
- `accountStocks` - Ações associadas à conta

#### 📈 **Stock (Ação)**
- `stockId` (String) - Código da ação (ex: PETR4)
- `description` - Descrição da ação

#### 💰 **Transaction (Transação)**
- `id` (UUID) - Identificador único
- `account` - Conta da transação
- `stock` - Ação negociada
- `tipo` - Tipo (COMPRA ou VENDA)
- `quantidade` - Quantidade de ações
- `preco` - Preço unitário
- `data` - Data da transação

## 🔌 Integração com APIs Externas

### API Brapi
A aplicação integra com a **API Brapi** para obter cotações de ações em tempo real:

```java
@FeignClient(name = "BrapiClient", url = "https://brapi.dev")
public interface BrapiClient {
    @GetMapping(value = "/api/quote/{stockId}")
    BrapiResponseDto getQuote(
        @RequestParam("token") String token,
        @PathVariable("stockId") String stockId
    );
}
```

## 🚀 Como Executar a Aplicação

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+

### 1. Configuração do Banco de Dados

Crie um banco MySQL chamado `bd_invest` e configure as credenciais no arquivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bd_invest
spring.datasource.username=root
spring.datasource.password=sua_senha
```

### 2. Executar a Aplicação

```bash
# Clonar o repositório
git clone <url-do-repositorio>
cd AgredadorInvestimentos

# Compilar e executar
mvn clean install
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 3. Acessar a Documentação

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## 📝 Exemplos de Uso da API

### 1. Criar um Usuário

```bash
POST /v1/users
Content-Type: application/json

{
  "username": "João Silva",
  "email": "joao@email.com",
  "password": "minhasenha123"
}
```

### 2. Fazer Login

```bash
POST /v1/auth/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "password": "minhasenha123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": "123e4567-e89b-12d3-a456-426614174000",
  "username": "João Silva"
}
```

### 3. Criar uma Conta (com token)

```bash
POST /v1/users/{userId}/accounts
Authorization: Bearer <token>
Content-Type: application/json

{
  "description": "Conta Corrente Principal",
  "street": "Rua das Flores, 123",
  "number": "123"
}
```

### 4. Listar Contas do Usuário

```bash
GET /v1/users/{userId}/accounts
Authorization: Bearer <token>
```

### 5. Registrar uma Transação

```bash
POST /v1/transactions
Authorization: Bearer <token>
Content-Type: application/json

{
  "accountId": "123e4567-e89b-12d3-a456-426614174000",
  "stockId": "PETR4",
  "tipo": "COMPRA",
  "quantidade": 100,
  "preco": 25.50
}
```

## 🛡️ Validações e Segurança

### Validações de Dados
- **Username**: 3-50 caracteres, obrigatório
- **Email**: Formato válido, obrigatório
- **Senha**: Mínimo 8 caracteres, obrigatória
- **Transações**: Quantidade e preço devem ser positivos

### Segurança Implementada
- ✅ Criptografia de senhas com BCrypt
- ✅ Autenticação JWT stateless
- ✅ Filtros de segurança para endpoints protegidos
- ✅ Validação de tokens em todas as requisições
- ✅ Headers de segurança configurados

## 🧪 Testando a Aplicação

### Usando cURL

```bash
# 1. Criar usuário
curl -X POST http://localhost:8080/v1/users \
  -H "Content-Type: application/json" \
  -d '{"username":"Teste","email":"teste@email.com","password":"12345678"}'

# 2. Fazer login
curl -X POST http://localhost:8080/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"teste@email.com","password":"12345678"}'

# 3. Usar token para acessar endpoint protegido
curl -X GET http://localhost:8080/v1/users \
  -H "Authorization: Bearer <token_retornado>"
```

### Usando Postman/Insomnia
Importe a coleção de requests ou use a documentação Swagger para testar os endpoints.

## 📈 Funcionalidades Futuras

- [ ] Dashboard com gráficos de performance
- [ ] Notificações de preços
- [ ] Relatórios de lucro/prejuízo
- [ ] Integração com mais corretoras
- [ ] Sistema de alertas
- [ ] Exportação de dados para Excel/PDF

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Desenvolvedor

**Brenno** - Desenvolvedor Full Stack

---

## 🔧 Configurações Avançadas

### Variáveis de Ambiente

Você pode configurar as seguintes variáveis de ambiente:

```bash
# Banco de dados
DB_URL=jdbc:mysql://localhost:3306/bd_invest
DB_USERNAME=root
DB_PASSWORD=sua_senha

# JWT
JWT_SECRET=seu_secret_super_seguro_aqui
JWT_EXPIRATION=86400000

# API Externa
BRAPI_TOKEN=seu_token_da_brapi
```

### Logs

A aplicação utiliza logging configurável. Para ativar logs detalhados:

```properties
logging.level.brenno.AgredadorInvestimentos=DEBUG
logging.level.org.springframework.security=DEBUG
```

---

**🎉 Obrigado por usar o Agregador de Investimentos!**
