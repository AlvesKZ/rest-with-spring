# REST with Spring Boot

API RESTful desenvolvida com **Spring Boot 3**, aplicando boas práticas de desenvolvimento como content negotiation, HATEOAS, paginação com ordenação, busca por nome, importação e exportação de arquivos CSV/XLSX, upload e download de arquivos, testes de integração com Testcontainers e documentação automática com Swagger/OpenAPI.

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
| Apache Commons CSV | — |
| Apache POI (XLSX) | — |
| Testcontainers | 1.20.4 |
| REST Assured | — |

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
│   ├── FileController.java         # Upload e download de arquivos
│   ├── TestLogController.java      # Endpoint de teste de logs (SLF4J)
│   └── docs/                       # Interfaces com anotações OpenAPI
├── services/
│   ├── PersonServices.java
│   ├── BookServices.java
│   └── FileStorageServices.java
├── file/
│   ├── exporter/
│   │   ├── MediaTypes.java         # Constantes de media type (XLSX, CSV)
│   │   ├── contract/FileExporter.java
│   │   ├── factory/FileExporterFactory.java
│   │   └── impl/
│   │       ├── CsvExporter.java
│   │       └── XlsxExporter.java
│   └── importer/
│       ├── contract/FileImporter.java
│       ├── factory/FileImporterFactory.java
│       └── impl/
│           ├── CsvImporter.java
│           └── XlsxImporter.java
├── repository/
│   ├── PersonRepository.java
│   └── BookRepository.java
├── model/
│   ├── Person.java
│   └── Book.java
├── data/dto/
│   ├── PersonDTO.java
│   ├── BookDTO.java
│   └── UploadFileResponseDTO.java
├── mapper/
│   └── ObjectMapper.java           # Mapeamento entre entidades e DTOs
├── serialization/converter/
│   └── YamlJackson2HttpMessageConverter.java
├── exception/
│   ├── handler/CustomEntityResponseHandler.java
│   ├── ExceptionResponse.java
│   ├── BadRequestException.java
│   ├── FileNotFoundException.java
│   ├── FileStorageException.java
│   ├── ResourceNotFoundException.java
│   └── RequiredObjectIsNullException.java
└── Startup.java

src/main/resources/
├── application.yml
└── db/migration/                   # Scripts Flyway
    ├── V1__Create_Table_Person.sql
    ├── V2__Populate_Table_Person.sql
    ├── V3__Create_Table_Books.sql
    ├── V4__Insert_Data_In_Books.sql
    ├── V5__Alter_Table_Person.sql        # Adiciona coluna enabled
    └── V6__Populate_Person_With_Many.sql # Popula tabela com 1000+ registros

src/test/java/com/noxus/
├── config/
│   └── TestConfigs.java
├── integrationtests/
│   ├── controllers/
│   │   ├── cors/withjson/
│   │   │   └── PersonControllerCorsTest.java
│   │   ├── withjson/
│   │   │   ├── PersonControllerJsonTest.java
│   │   │   └── BookControllerJsonTest.java
│   │   ├── withxml/
│   │   │   ├── PersonControllerXmlTest.java
│   │   │   └── BookControllerXmlTest.java
│   │   └── withyaml/
│   │       ├── PersonControllerYamlTest.java
│   │       ├── BookControllerYamlTest.java
│   │       └── mapper/YAMLMapper.java
│   ├── dto/
│   │   ├── PersonDTO.java
│   │   ├── BookDTO.java
│   │   └── wrappers/               # Wrappers para paginação (JSON, XML, YAML)
│   ├── swagger/
│   │   └── SwaggerIntegrationTest.java
│   └── testcontainers/
│       └── AbstractIntegrationTest.java
├── repository/
│   └── PersonRepositoryTest.java
└── unittests/
    ├── mapper/
    │   ├── ObjectMapperTests.java
    │   └── mocks/
    └── services/
        ├── PersonServicesTest.java
        └── BookServicesTest.java
```

---

## Funcionalidades

- CRUD completo para **Person** e **Book**
- **Paginação e ordenação** — todos os endpoints de listagem aceitam `page`, `size` e `direction`
- **Busca por nome** — `/api/person/v1/findPeopleByName/{firstName}` com paginação
- **Desativação de Person** — `PATCH /api/person/v1/{id}` alterna o campo `enabled` sem excluir o registro
- **Importação em massa** — `POST /api/person/v1/massCreation` aceita arquivos `.csv` ou `.xlsx` e persiste os registros
- **Exportação paginada** — `GET /api/person/v1/exportPage` retorna `.csv` ou `.xlsx` conforme o header `Accept`
- **Upload e download de arquivos** — armazenamento local via `FileStorageServices`
- **Content Negotiation** via header `Accept` — suporte a JSON, XML e YAML
- **HATEOAS** — respostas com hypermedia links e `PagedModel`
- **Documentação automática** com Swagger UI (SpringDoc OpenAPI)
- **Versionamento de banco de dados** com Flyway (6 migrations)
- **Mapeamento DTO ↔ Entidade** com Dozer Mapper
- **Tratamento global de exceções** com `@RestControllerAdvice`
- **Testes unitários** para services e mappers (JUnit/Mockito)
- **Testes de integração** com Testcontainers + REST Assured (JSON, XML, YAML)
- **Testes de CORS** para validar origens permitidas
- **Endpoint de log** para testes de níveis DEBUG, INFO, WARN e ERROR (SLF4J)

---

## Endpoints

### Person — `/api/person/v1`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/person/v1` | Lista pessoas com paginação (`page`, `size`, `direction`) |
| `GET` | `/api/person/v1/{id}` | Busca pessoa por ID |
| `GET` | `/api/person/v1/findPeopleByName/{firstName}` | Busca pessoas por nome com paginação |
| `GET` | `/api/person/v1/exportPage` | Exporta página de pessoas em `.xlsx` ou `.csv` (via header `Accept`) |
| `POST` | `/api/person/v1` | Cria uma nova pessoa |
| `POST` | `/api/person/v1/massCreation` | Importa pessoas a partir de arquivo `.csv` ou `.xlsx` |
| `PUT` | `/api/person/v1` | Atualiza uma pessoa |
| `PATCH` | `/api/person/v1/{id}` | Ativa ou desativa uma pessoa (campo `enabled`) |
| `DELETE` | `/api/person/v1/{id}` | Remove uma pessoa |

### Book — `/api/book/v1`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/book/v1` | Lista livros com paginação (`page`, `size`, `direction`) |
| `GET` | `/api/book/v1/{id}` | Busca livro por ID |
| `POST` | `/api/book/v1` | Cria um novo livro |
| `PUT` | `/api/book/v1` | Atualiza um livro |
| `DELETE` | `/api/book/v1/{id}` | Remove um livro |

### Arquivos — `/api/file/v1`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/file/v1/uploadFile` | Faz upload de um único arquivo |
| `POST` | `/api/file/v1/uploadMultipleFiles` | Faz upload de múltiplos arquivos |
| `GET` | `/api/file/v1/downloadFile/{fileName}` | Faz download de um arquivo pelo nome |

### Utilitário — `/api/test/v1`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/test/v1` | Gera logs em todos os níveis (DEBUG, INFO, WARN, ERROR) |

---

## Configuração

### Pré-requisitos

- Java 21+
- Maven 3.x
- MySQL 8+
- Docker (necessário para rodar os testes de integração com Testcontainers)

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
# Todos os testes (unitários + integração)
mvn test
```

> Os testes de integração sobem um container MySQL automaticamente via **Testcontainers**. É necessário ter o Docker rodando.

---

## Licença

Distribuído sob a licença **Apache 2.0**. Veja [LICENSE](https://www.apache.org/licenses/LICENSE-2.0) para mais informações.