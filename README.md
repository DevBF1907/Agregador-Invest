# Agregador de Investimentos API

API desenvolvida em **Java Spring Boot** para gerenciar **usuários, contas, ações e transações**, com integração à **BRApiClient** para obter cotações em tempo real e calcular o valor total da carteira de ações.

---

## 🔹 Tecnologias Utilizadas

- Java 17  
- Spring Boot  
- Spring Data JPA  
- MySQL  
- OpenFeign (para integração com BRApi)  
- Springdoc OpenAPI / Swagger UI  

---

## 🔹 Estrutura da API

A API possui as seguintes **entidades principais**:

- **User** → representa o usuário do sistema.  
- **Account** → conta financeira do usuário.  
- **Stock** → ações disponíveis no sistema.  
- **AccountStock** → relação entre contas e ações (quantidade de cada ação em cada conta).  
- **Transaction** → registro de compra ou venda de ações.  
- **TransactionType** (enum) → BUY ou SELL.  
- **BillingAddress** → endereço de cobrança da conta.  

A API integra-se à **BRApiClient** para obter o preço atual das ações e calcular o **valor total da carteira**.

---

## 🔹 Fluxo da API

1. **Usuário (User)**
   - Cria uma conta (**Account**) via `/v1/users/{userId}/accounts`.  
   - Pode atualizar ou deletar seus dados via endpoints `/v1/users`.  

2. **Conta (Account)**
   - Cada conta pode ter várias ações associadas (**AccountStock**).  
   - O usuário pode listar as ações e seus valores totais via `/v1/accounts/{accountId}/stocks`.  

3. **Ações (Stock)**
   - Ações são criadas no sistema via `/v1/stocks`.  
   - Cada ação possui `stockId` e `description`.  

4. **Transações (Transaction)**
   - Usuário realiza transações de compra ou venda de ações via `/accounts/{accountId}/transactions`.  
   - Cada transação atualiza a quantidade de ações na conta e o saldo da conta.  

5. **Integração com BRApiClient**
   - Para cada ação associada a uma conta, a API consulta a BRApi para obter o preço atual.  
   - O valor total da ação é calculado como:  

```text
totalValue = currentPrice * quantity


🔹 Endpoints Principais
Usuários (/v1/users)
| Método | Endpoint                      | Descrição                              |
| ------ | ----------------------------- | -------------------------------------- |
| POST   | `/v1/users`                   | Cria um novo usuário                   |
| GET    | `/v1/users`                   | Lista todos os usuários                |
| GET    | `/v1/users/{userId}`          | Retorna dados de um usuário específico |
| PUT    | `/v1/users/{userId}`          | Atualiza dados do usuário              |
| DELETE | `/v1/users/{userId}`          | Remove um usuário                      |
| POST   | `/v1/users/{userId}/accounts` | Cria uma conta associada ao usuário    |
| GET    | `/v1/users/{userId}/accounts` | Lista todas as contas de um usuário    |

Contas (/v1/accounts)
| Método | Endpoint                          | Descrição                                              |
| ------ | --------------------------------- | ------------------------------------------------------ |
| POST   | `/v1/accounts/{accountId}/stocks` | Associa ações a uma conta                              |
| GET    | `/v1/accounts/{accountId}/stocks` | Lista ações de uma conta com preço atual e valor total |


Ações (/v1/stocks)
| Método | Endpoint     | Descrição                     |
| ------ | ------------ | ----------------------------- |
| POST   | `/v1/stocks` | Cria uma nova ação no sistema |

Transações (/accounts/{accountId}/transactions)
| Método | Endpoint                             | Descrição                                          |
| ------ | ------------------------------------ | -------------------------------------------------- |
| POST   | `/accounts/{accountId}/transactions` | Registra uma transação de compra ou venda de ações |

🔹 Documentação Swagger

Após rodar a aplicação, acesse:


http://localhost:8080/swagger-ui.html


Todos os endpoints, DTOs e enums estão documentados.

Permite testar a API diretamente no navegador.

🔹 Como Rodar a API

Clone o repositório:

git clone <seu-repositorio>


Configure o banco de dados MySQL e ajuste application.properties ou application.yml.

Rode a aplicação:

mvn spring-boot:run


Teste os endpoints via Swagger UI ou qualquer cliente HTTP (Postman, Insomnia, etc.).

🔹 Fluxo da Carteira (Resumo)
User → cria Account → associa Stocks → realiza Transactions → consulta valor total
                  ↑
                  Stock (preço atual via BRApiClient)


A cada transação, a quantidade de ações na conta é atualizada.

O valor total da carteira é calculado usando o preço atual das ações.

Esse fluxo garante que o usuário sempre veja o saldo e valor atualizado da sua carteira de investimentos.

