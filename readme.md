```markdown
# Library API

A aplicação **Library API** é um sistema desenvolvido utilizando **Spring Boot** que permite gerenciar uma biblioteca, incluindo o cadastro de livros, autores e usuários, além de garantir a segurança através da autenticação e autorização utilizando Spring Security.

## Descrição do Projeto

O projeto gerencia as entidades de uma biblioteca, com funcionalidades como:

- Cadastro e busca de **autores** com validações e controle de duplicidade.
- Registro e consulta de **livros**, com busca por título, autor, ISBN, gênero e ano de publicação.
- Gerenciamento de **usuários**, com autenticação e autorização.
- Controle de acessos baseados em perfil (**ROLE_ADMIN**, **ROLE_USER**, etc).

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.3.4**
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring Validation
- **PostgreSQL** como banco de dados relacional.
- **MapStruct** para mapeamento de objetos DTO para entidades.
- **Lombok** para redução de código boilerplate.
- **Thymeleaf** para renderização da página de login customizada (frontend).
- **Hypersistence Utils** para facilitar o uso de recursos avançados do Hibernate.

## Estrutura do Projeto

### Pacotes

- `com.filipecode.libraryApi.model.dtos`  
  Contém os objetos de transferência de dados (**DTOs**) para requisição e resposta.

- `com.filipecode.libraryApi.model.entities`  
  Define as entidades mapeadas para tabelas do banco de dados, como `Author` e `Book`.

- `com.filipecode.libraryApi.controller`  
  Define os controladores REST responsáveis por expor endpoints para gerenciar livros, autores e usuários.  

- `com.filipecode.libraryApi.repositories`  
  Repositórios que interagem diretamente com o banco de dados usando JPA e Spring Data.

- `com.filipecode.libraryApi.service`  
  Camada de serviço que contém as regras de negócio da aplicação.

- `com.filipecode.libraryApi.security`  
  Configurações de segurança para autenticação e autorização.

- `com.filipecode.libraryApi.validator`  
  Inclui validações customizadas para entidades.

- `com.filipecode.libraryApi.exceptions`  
  Exceptions customizadas para lidar com situações como duplicidade de registro ou operação não permitida.

- `com.filipecode.libraryApi.repositories.specs`  
  Especificações do Spring Data JPA para consultas dinâmicas de livros.

### Entidades Principais

#### Author

Representa o autor do livro.

- **Atributos:**
  - `id`: UUID
  - `name`: Nome do autor (máx. 100 caracteres)
  - `dateOfBirth`: Data de nascimento
  - `nationality`: Nacionalidade (máx. 50 caracteres)
  - `books`: Lista de livros relacionados a este autor

#### Book

Representa os livros cadastrados na biblioteca.

- **Atributos:**
  - `id`: UUID
  - `isbn`: Código único do livro
  - `title`: Título do livro
  - `publicationDate`: Data de publicação
  - `gender`: Gênero do livro (Enum: FICCAO, FANTASIA, MISTERIO, etc.)
  - `price`: Preço do livro

#### UserLogin

Representa os usuários que podem acessar o sistema.

- **Atributos:**
  - `id`: UUID
  - `login`: Nome de usuário único
  - `password`: Senha (segura e criptografada)
  - `roles`: Perfil(s) associado(s) ao usuário

### Endpoints

#### Gerenciamento de Livros

- **POST /livros**  
  Cadastrar um livro (requer `ROLE_OPERADOR` ou `ROLE_GERENTE`).

- **GET /livros/{id}**  
  Consultar detalhes de um livro pelo ID.

- **DELETE /livros/{id}**  
  Remover um livro do sistema.

- **GET /livros**  
  Buscar livros com filtros (ISBN, título, gênero, autor, etc.).

- **PUT /livros/{id}**  
  Atualizar dados de um livro.

#### Gerenciamento de Autores

- **POST /autores**  
  Cadastrar um autor (requer `ROLE_GERENTE`).

- **GET /autores/{id}**  
  Consultar detalhes de um autor.

- **DELETE /autores/{id}**  
  Remover um autor (apenas se ele não for relacionado a livros).

- **GET /autores**  
  Listar autores com filtros opcionais de nome e nacionalidade.

- **PUT /autores/{id}**  
  Atualizar os dados de um autor existente.

#### Gerenciamento de Usuários

- **POST /usuarios**  
  Registrar um novo usuário.

#### Autenticação

- **GET /login**  
  Página customizada de login.

### Segurança

Foi configurado um sistema baseado em tokens de autenticação via Spring Security:

- Autorização baseada em **roles** utilizando anotações como `@PreAuthorize`.
- Uso do MapStruct para criar mapeadores entre entidades e DTOs de forma eficiente.

### Regras de Negócio

- Validação de duplicidade para autores (`AuthorValidator`) e livros (`BookValidator`).
- Impossibilidade de remover autores que possuem livros cadastrados.
- Preços obrigatórios para livros publicados a partir de 2020.

## Banco de Dados

### Estrutura das Tabelas
``` sql
create table autor(
  id uuid not null primary key,
  nome varchar(100) not null,
  data_nascimento date not null,
  nacionalidade varchar(50) not null,
  data_cadastro timestamp,
  data_atualizacao timestamp,
  id_usuario uuid
);

create table livro (
   id uuid not null primary key,
   isbn varchar(20) not null unique,
   titulo varchar(150) not null,
   data_publicacao date not null,
   genero varchar(30) not null,
   preco numeric(18,2),
   data_cadastro timestamp,
   data_atualizacao timestamp,
   id_usuario uuid,
   id_autor uuid not null references autor(id),
   constraint chk_genero check (genero in ('FICCAO', 'FANTASIA', 'MISTERIO','ROMANCE', 'BIOGRAFIA', 'CIENCIA') )
);

create table usuario(
    id UUID NOT NULL PRIMARY KEY,
    login VARCHAR(20) NOT NULL UNIQUE,
    senha VARCHAR(300) NOT NULL,
    roles VARCHAR[]
);
```
## Requisitos

- **Java 21**
- **PostgreSQL**
- **Maven**

## Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone <repository-url>
   ```

2. Configure o arquivo `application.properties` com as credenciais do banco de dados.

3. Execute o comando Maven para rodar a aplicação:
   ```bash
   mvn spring-boot:run
   ```

4. Acesse a aplicação em [http://localhost:8080](http://localhost:8080).

## Autor
Desenvolvido por **Filipe Mota**.```

