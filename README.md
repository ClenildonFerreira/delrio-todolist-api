# delrio-todolist-api

Projeto Spring Boot (API) — lista de tarefas (ToDo).

## Descrição

API RESTful para gerenciamento de tarefas (ToDo list) desenvolvida com Spring Boot. Permite criar, listar, atualizar e excluir tarefas com recursos como priorização, status e paginação.

## Funcionalidades

- Criar novas tarefas
- Listar tarefas com paginação
- Atualizar informações das tarefas
- Excluir tarefas
- Suporte a diferentes status (Aberta, Em Andamento, Concluída)
- Priorização de tarefas

## Tecnologias

- Java 17
- Spring Boot 3.5.5
- MySQL
- Maven

## Configuração

O projeto utiliza MySQL como banco de dados. Configure as credenciais no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/todolist_db
spring.datasource.username=root
spring.datasource.password=root
```

## Execução

1. Certifique-se de ter o Java 17+ e Maven instalados
2. Configure o banco de dados - qualquer banco da sua preferência
3. Execute o projeto:

```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## Endpoints da API

A API possui os seguintes endpoints principais:

- **POST /todos** - Cria uma nova tarefa
- **GET /todos?page={pagina}&size={tamanho}** - Lista tarefas com paginação
- **PATCH /todos/{id}** - Atualiza parcialmente uma tarefa existente
- **DELETE /todos/{id}** - Exclui uma tarefa

## Documentação da API

A documentação da API está disponível através do Swagger UI:

```
http://localhost:8080/swagger
```