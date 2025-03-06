# Bank Service API

## Descrição

Este projeto é uma API bancária desenvolvida em **Spring Boot**, permitindo o gerenciamento de clientes e transferências bancárias.

## Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database
- OpenAPI (Swagger)

## Instalação e Configuração

### **Requisitos**

- JDK 17+
- Maven 3.8+

### **Passos para rodar o projeto**

1. Clone o repositório:

   git clone https://github.com/keterlyvb/bank-service.git


2. Compile e rode o projeto:

   ```sh
   mvn spring-boot:run
   ```

3. A API estará disponível em:

   ```
   http://localhost:8080
   ```

## **Endpoints**

A API disponibiliza os seguintes endpoints:

### **Clientes**

- `POST /v1/customers` → Cria um novo cliente.
- `GET /v1/customers` → Retorna todos os clientes.
- `GET /v1/customers/{accountNumber}` → Retorna um cliente pelo número da conta.

### **Transferências**

- `POST /v1/customers/transfer` → Realiza uma transferência entre contas.
- `GET /v1/customers/transfers/{accountNumber}` → Lista transferências de um cliente.


