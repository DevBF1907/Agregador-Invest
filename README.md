Agregador de Investimentos API

API desenvolvida em Java Spring Boot para gerenciar usuários, contas, ações e transações, com integração à BRApiClient para obter cotações em tempo real e calcular o valor total da carteira de ações.

🔹 Tecnologias Utilizadas

Java 17

Spring Boot

Spring Data JPA

MySQL

OpenFeign (para integração com BRApi)

Springdoc OpenAPI / Swagger UI

🔹 Estrutura da API

A API possui as seguintes entidades principais:

User → representa o usuário do sistema.

Account → conta financeira do usuário.

Stock → ações disponíveis no sistema.

AccountStock → relação entre contas e ações (quantidade de cada ação em cada conta).

Transaction → registro de compra ou venda de ações.

TransactionType (enum) → BUY ou SELL.

BillingAddress → endereço de cobrança da conta.

A API integra-se à BRApiClient para obter o preço atual das ações e calcular o valor total da carteira.

🔹 Endpoints Principais
1️⃣ Usuários (/v1/users)
Método	Endpoint	Descrição
POST	/v1/users	Cria um novo usuário
GET	/v1/users	Lista todos os usuários
GET	/v1/users/{userId}	Retorna dados de um usuário específico
PUT	/v1/users/{userId}	Atualiza dados do usuário
DELETE	/v1/users/{userId}	Remove um usuário
POST	/v1/users/{userId}/accounts	Cria uma conta associada ao usuário
GET	/v1/users/{userId}/accounts	Lista todas as contas de um usuário

Exemplo de Request para criar usuário:

{
  "name": "Brenno",
  "email": "brenno@email.com",
  "password": "123456"
}

2️⃣ Contas (/v1/accounts)
Método	Endpoint	Descrição
POST	/v1/accounts/{accountId}/stocks	Associa ações a uma conta
GET	/v1/accounts/{accountId}/stocks	Lista ações de uma conta com preço atual e valor total

Exemplo de Request para associar ações:

{
  "stockId": "AAPL",
  "quantity": 10
}


Exemplo de Response:

[
  {
    "stockId": "AAPL",
    "quantity": 10,
    "currentPrice": 150.5,
    "totalValue": 1505.0
  }
]

3️⃣ Ações (/v1/stocks)
Método	Endpoint	Descrição
POST	/v1/stocks	Cria uma nova ação no sistema

Exemplo de Request:

{
  "stockId": "AAPL",
  "description": "Apple Inc."
}

4️⃣ Transações (/accounts/{accountId}/transactions)
Método	Endpoint	Descrição
POST	/accounts/{accountId}/transactions	Registra uma transação de compra ou venda de ações

Exemplo de Request:

{
  "stockId": "AAPL",
  "tipo": "BUY",
  "quantidade": 10,
  "preco": 150.5
}


Exemplo de Response:

{
  "transactionId": "uuid-transacao",
  "accountId": "uuid-conta",
  "stockId": "AAPL",
  "tipo": "BUY",
  "quantidade": 10,
  "preco": 150.5,
  "result": "SUCCESS",
  "balanceAfter": 5000
}



O serviço calcula o valor total da carteira multiplicando o preço atual pelo número de ações em cada conta.

🔹 Documentação Swagger

Após rodar a aplicação, você pode acessar a interface do Swagger UI:

http://localhost:8080/swagger-ui.html


Todos os endpoints, DTOs e enums estão documentados.

Permite testar os endpoints diretamente no navegador.

🔹 Como Rodar a API

Clone o repositório:

git clone <seu-repositorio>


Configure o banco de dados MySQL e ajuste application.properties ou application.yml.

Rode a aplicação:

mvn spring-boot:run


Acesse o Swagger UI para testar os endpoints.
