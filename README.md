# REST with Spring Boot

API RESTful desenvolvida com **Spring Boot 3**, aplicando boas práticas de desenvolvimento como autenticação JWT, content negotiation, HATEOAS, paginação com ordenação, busca por nome, importação e exportação de arquivos CSV/XLSX/PDF, upload e download de arquivos, envio de e-mails, geração de QR Code, testes de integração com Testcontainers e documentação automática com Swagger/OpenAPI.

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 21 |
| Spring Boot | 3.4.0 |
| Spring Security | — |
| Spring Data JPA | — |
| Spring HATEOAS | — |
| Spring Mail | — |
| MySQL | — |
| Flyway | — |
| SpringDoc OpenAPI (Swagger) | 2.7.0 |
| JWT (java-jwt) | — |
| Dozer Mapper | 7.0.0 |
| Jackson (JSON / XML / YAML) | — |
| Apache Commons CSV | — |
| Apache POI (XLSX) | — |
| iText (PDF) | — |
| ZXing (QR Code) | — |
| Testcontainers | 1.20.4 |
| REST Assured | — |

---

## Estrutura do Projeto

```
src/main/java/com/noxus/
├── config/
│   ├── EmailConfig.java                # Configuração do JavaMailSender
│   ├── FileStorageConfig.java          # Configuração do diretório de upload
│   ├── OpenApiConfig.java              # Configuração do Swagger/OpenAPI
│   ├── SecurityConfig.java             # Configuração do Spring Security + JWT
│   └── WebConfig.java                  # Content negotiation (JSON, XML, YAML)
├── controllers/
│   ├── AuthController.java             # Login, refresh token e criação de usuário
│   ├── BookController.java             # Endpoints de Book
│   ├── EmailController.java            # Envio de e-mails simples e com anexo
│   ├── FileController.java             # Upload e download de arquivos
│   ├── PersonController.java           # Endpoints de Person
│   ├── TestLogController.java          # Endpoint de teste de logs (SLF4J)
│   └── docs/                           # Interfaces com anotações OpenAPI
├── services/
│   ├── AuthService.java                # Autenticação, refresh token e criação de usuário
│   ├── BookService.java
│   ├── EmailService.java               # Envio de e-mails via JavaMailSender
│   ├── FileStorageService.java
│   ├── PersonService.java
│   ├── QRCodeService.java              # Geração de QR Code com ZXing
│   └── UserService.java                # UserDetailsService para o Spring Security
├── security/jwt/
│   ├── JwtTokenFilter.java             # Filtro que intercepta requisições e valida o token
│   └── JwtTokenProvider.java           # Criação, validação e resolução de tokens JWT
├── file/
│   ├── exporter/
│   │   ├── MediaTypes.java             # Constantes de media type (XLSX, CSV, PDF)
│   │   ├── contract/PersonExporter.java
│   │   ├── factory/FileExporterFactory.java
│   │   └── impl/
│   │       ├── CsvExporter.java
│   │       ├── PdfExporter.java
│   │       └── XlsxExporter.java
│   └── importer/
│       ├── contract/FileImporter.java
│       ├── factory/FileImporterFactory.java
│       └── impl/
│           ├── CsvImporter.java
│           └── XlsxImporter.java
├── mail/
│   └── EmailSender.java
├── repository/
│   ├── BookRepository.java
│   ├── PersonRepository.java
│   └── UserRepository.java
├── model/
│   ├── Book.java
│   ├── Permission.java
│   ├── Person.java
│   └── User.java
├── data/dto/
│   ├── BookDTO.java
│   ├── PersonDTO.java
│   ├── UploadFileResponseDTO.java
│   ├── request/EmailRequestDTO.java
│   └── security/
│       ├── AccountCredentialsDTO.java
│       └── TokenDTO.java
├── mapper/
│   └── ObjectMapper.java               # Mapeamento entre entidades e DTOs
├── serialization/converter/
│   └── YamlJackson2HttpMessageConverter.java
├── exception/
│   ├── handler/CustomEntityResponseHandler.java
│   ├── ExceptionResponse.java
│   ├── BadRequestException.java
│   ├── FileNotFoundException.java
│   ├── FileStorageException.java
│   ├── InvalidJwtAuthenticationException.java
│   ├── ResourceNotFoundException.java
│   └── RequiredObjectIsNullException.java
└── Startup.java

src/main/resources/
├── application.yml
└── db/migration/                       # Scripts Flyway (18 migrations)
    ├── V1__Create_Table_Person.sql
    ├── V2__Populate_Table_Person.sql
    ├── V3__Create_Table_Books.sql
    ├── V4__Insert_Data_In_Books.sql
    ├── V5__Alter_Table_Person.sql
    ├── V6__Populate_Person_With_Many.sql
    ├── V7__Populate_Books_With_Many.sql
    ├── V8__Alter_Table_Person_Adding_Photo_And_Wiki.sql
    ├── V9__Inserting_Wikipedia_Url.sql
    ├── V10__Inserting_Photo_Url.sql
    ├── V11__Create_Join_Table_Person_Books.sql
    ├── V12__Insert_Data_In_Join_Table_Person_Books.sql
    ├── V13__Create_Table_Permission.sql
    ├── V14__Insert_Data_In_Permission.sql
    ├── V15__Create_Table_Users.sql
    ├── V16__Insert_Data_In_Users.sql
    ├── V17__Create_Table_User_Permission.sql
    └── V18__Insert_Data_In_User_Permission.sql

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
│   │   └── wrappers/
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

- **Autenticação JWT** — login via `/auth/signin`, refresh token via `/auth/refresh/{username}` e criação de usuário via `/auth/createUser`
- **Autorização por roles** — permissões `ADMIN`, `MANAGER` e `COMMON_USER` gerenciadas pelo Spring Security
- CRUD completo para **Person** e **Book**
- **Paginação e ordenação** — todos os endpoints de listagem aceitam `page`, `size` e `direction`
- **Busca por nome** — `/api/person/v1/findPeopleByName/{firstName}` com paginação
- **Desativação de Person** — `PATCH /api/person/v1/{id}` alterna o campo `enabled` sem excluir o registro
- **Importação em massa** — `POST /api/person/v1/massCreation` aceita arquivos `.csv` ou `.xlsx`
- **Exportação paginada** — `GET /api/person/v1/exportPage` retorna `.csv`, `.xlsx` ou `.pdf` conforme o header `Accept`
- **Upload e download de arquivos** — armazenamento local via `FileStorageService`
- **Envio de e-mails** — e-mail simples e com anexo via Spring Mail (SMTP Gmail)
- **Geração de QR Code** — geração de imagem PNG a partir de uma URL usando ZXing
- **Content Negotiation** via header `Accept` — suporte a JSON, XML e YAML
- **HATEOAS** — respostas com hypermedia links e `PagedModel`
- **Documentação automática** com Swagger UI (SpringDoc OpenAPI)
- **Versionamento de banco de dados** com Flyway (18 migrations)
- **Mapeamento DTO ↔ Entidade** com Dozer Mapper
- **Tratamento global de exceções** com `@RestControllerAdvice`
- **Testes unitários** para services e mappers (JUnit/Mockito)
- **Testes de integração** com Testcontainers + REST Assured (JSON, XML, YAML)
- **Testes de CORS** para validar origens permitidas
- **Endpoint de log** para testes de níveis DEBUG, INFO, WARN e ERROR (SLF4J)

---

## Endpoints

### Autenticação — `/auth`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/auth/signin` | Realiza login e retorna o token JWT e o refresh token |
| `PUT` | `/auth/refresh/{username}` | Renova o token JWT usando o refresh token |
| `POST` | `/auth/createUser` | Cria um novo usuário |

### Person — `/api/person/v1`

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/person/v1` | Lista pessoas com paginação (`page`, `size`, `direction`) |
| `GET` | `/api/person/v1/{id}` | Busca pessoa por ID |
| `GET` | `/api/person/v1/findPeopleByName/{firstName}` | Busca pessoas por nome com paginação |
| `GET` | `/api/person/v1/exportPage` | Exporta página de pessoas em `.xlsx`, `.csv` ou `.pdf` (via header `Accept`) |
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

### E-mail — `/api/email/v1`

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/email/v1` | Envia um e-mail simples |
| `POST` | `/api/email/v1/withAttachment` | Envia um e-mail com anexo |

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

> ⚠️ Nunca manipule as tabelas manualmente enquanto o Flyway estiver gerenciando o schema. Isso pode corromper o histórico de migrations.

### E-mail

Configure as variáveis de ambiente para o envio de e-mails via Gmail:

```bash
EMAIL_USERNAME=seu_email@gmail.com
EMAIL_PASSWORD=sua_senha_de_app
```

Ou configure diretamente no `application.yml`:

```yaml
spring:
  mail:
    username: seu_email@gmail.com
    password: sua_senha_de_app
```

### JWT

A chave secreta e o tempo de expiração do token podem ser ajustados em `application.yml`:

```yaml
security:
  jwt:
    token:
      secret-key: sua_chave_secreta
      expire-length: 3600000  # 1 hora em milissegundos
```

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

Para testar endpoints protegidos, primeiro faça login em `POST /auth/signin`

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