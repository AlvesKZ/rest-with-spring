# REST with Spring Boot — Em Construção

> **⚠️Este projeto está ativamente em desenvolvimento.** Funcionalidades podem ser adicionadas, alteradas ou removidas a qualquer momento.

API RESTful desenvolvida com **Spring Boot 3**, aplicando boas práticas de desenvolvimento como content negotiation, HATEOAS, versionamento de banco de dados com Flyway e documentação automática com Swagger/OpenAPI.

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.0 |
| Spring Data JPA | — |
| Spring HATEOAS | — |
| MySQL | — |
| Flyway | — |
| SpringDoc OpenAPI (Swagger) | 2.7.0 |
| Dozer Mapper | 7.0.0 |
| Jackson (JSON / XML / YAML) | — |

---

## Estrutura do Projeto

```
src/main/java/com/noxus/
├── config/
│   ├── OpenApiConfig.java          # Configuração do Swagger/OpenAPI
│   └── WebConfig.java              # Content negotiation (JSON, XML, YAML)
├── controllers/
│   ├── PersonController.java       # Endpoints de Person
│   ├── BookController.java         # Endpoints de Book
│   └── docs/                       # Interfaces com anotações OpenAPI
├── services/
│   ├── PersonServices.java
│   └── BookServices.java
├── repository/
│   ├── PersonRepository.java
│   └── BookRepository.java
├── model/
│   ├── Person.java
│   └── Book.java
├── data/dto/
│   ├── PersonDTO.java
│   └── BookDTO.java
├── mapper/
│   └── ObjectMapper.java           # Mapeamento entre entidades e DTOs
├── serialization/converter/
│   └── YamlJackson2HttpMessageConverter.java
├── exception/
│   ├── handler/CustomEntityResponseHandler.java
│   ├── ExceptionResponse.java
│   ├── ResourceNotFoundException.java
│   └── RequiredObjectIsNullException.java
└── Startup.java

src/main/resources/
├── application.yml
└── db/migration/                   # Scripts Flyway
    ├── V1__Create_Table_Person.sql
    ├── V2__Populate_Table_Person.sql
    ├── V3__Create_Table_Books.sql
    └── V4__Insert_Data_In_Books.sql
```

---

## Funcionalidades Implementadas

- CRUD completo para **Person** e **Book**
- **Content Negotiation** via header `Accept` — suporte a JSON, XML e YAML
- **HATEOAS** — respostas com hypermedia links
- **Documentação automática** com Swagger UI (SpringDoc OpenAPI)
- **Versionamento de banco de dados** com Flyway
- **Mapeamento DTO ↔ Entidade** com Dozer Mapper
- **Tratamento global de exceções** com `@RestControllerAdvice`
- Testes unitários para services e mappers (JUnit/Mockito)

---

## Endpoints

### Person — `/api/person/v1`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/person/v1` | Lista todas as pessoas |
| `GET` | `/api/person/v1/{id}` | Busca pessoa por ID |
| `POST` | `/api/person/v1` | Cria uma nova pessoa |
| `PUT` | `/api/person/v1` | Atualiza uma pessoa |
| `DELETE` | `/api/person/v1/{id}` | Remove uma pessoa |

### Book — `/api/book/v1`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/book/v1` | Lista todos os livros |
| `GET` | `/api/book/v1/{id}` | Busca livro por ID |
| `POST` | `/api/book/v1` | Cria um novo livro |
| `PUT` | `/api/book/v1` | Atualiza um livro |
| `DELETE` | `/api/book/v1/{id}` | Remove um livro |

---

## Configuração

### Pré-requisitos

- Java 21+
- Maven 3.x
- MySQL 8+

### Banco de dados

Crie o banco de dados no MySQL:

```sql
CREATE DATABASE rest_with_spring;
```

Configure as credenciais em `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/rest_with_spring?useTimezone=true&serverTimezone=UTC
    username: seu_usuario
    password: sua_senha
```

As tabelas são criadas e populadas automaticamente pelo **Flyway** na primeira execução.

---

## Como Executar

```bash
# Clonar o repositório
git clone https://github.com/AlvesKZ/rest-with-spring
cd rest-with-spring

# Compilar e rodar
mvn spring-boot:run
```

A aplicação sobe na porta **8080** por padrão.

### Swagger UI

Acesse a documentação interativa em:

```
http://localhost:8080/swagger-ui/index.html
```

---

## Testes

```bash
mvn test
```

---

## Roadmap

- [x] CRUD de Person
- [x] CRUD de Book
- [x] Content Negotiation (JSON, XML, YAML)
- [x] HATEOAS
- [x] Swagger / OpenAPI
- [x] Flyway migrations
- [x] Testes unitários
- [ ] Autenticação e autorização (JWT)
- [ ] Testes de integração
- [ ] Paginação e ordenação
- [ ] Dockerização

---

##  Licença

Distribuído sob a licença **Apache 2.0**. Veja [LICENSE](https://www.apache.org/licenses/LICENSE-2.0) para mais informações.