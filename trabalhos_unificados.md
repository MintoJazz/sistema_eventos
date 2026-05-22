# 🧾 Trabalho Integrado — Sistema de Gerenciamento de Eventos com PostgreSQL, Java, JDBC, Javalin e Mustache

## 1. Introdução

Este trabalho propõe o desenvolvimento de um Sistema de Gerenciamento de Eventos utilizando:

- PostgreSQL
- Java
- JDBC
- Javalin
- Mustache

O projeto deve integrar conceitos de:

- modelagem relacional;
- consultas SQL;
- controle de acesso com DCL;
- transações JDBC;
- upload de arquivos;
- uso de JSONB;
- renderização server-side.

O objetivo é construir uma aplicação web organizada, segura e modular, seguindo práticas modernas de desenvolvimento backend.

---

# 2. Objetivos

O sistema deverá permitir:

- gerenciamento de eventos;
- gerenciamento de palestras e palestrantes;
- controle de inscrições;
- dashboards administrativos;
- relatórios;
- upload de arquivos;
- armazenamento flexível com JSONB;
- controle de acesso no PostgreSQL utilizando DCL.

---

# 3. Controle de Acesso com DCL

O sistema deverá utilizar controle de acesso baseado em roles e usuários técnicos do PostgreSQL.

O objetivo é aplicar o princípio do menor privilégio e separar responsabilidades de administração, migração estrutural e execução da aplicação.

---

## 3.1 Roles Obrigatórias

O banco de dados deverá possuir obrigatoriamente as seguintes roles:

| Role | Responsabilidade |
|---|---|
| `app_runtime` | Operações normais da aplicação |
| `app_migration` | Alterações estruturais e migrations |
| `app_admin` | Administração total do banco |

---

## 3.2 Usuários Técnicos Obrigatórios

O sistema deverá possuir obrigatoriamente os seguintes usuários técnicos:

| Usuário | Responsabilidade |
|---|---|
| `runtime_user` | Usuário utilizado pela aplicação Java durante execução |
| `migration_user` | Usuário utilizado para criação e atualização estrutural do banco |

---

## 3.3 Requisitos Obrigatórios de Permissões

### `app_runtime`

Deve possuir permissões apenas para:

- `SELECT`
- `INSERT`
- `UPDATE`
- `DELETE`

nas tabelas necessárias para operação da aplicação.

Não deve possuir permissões de:

- `CREATE`
- `ALTER`
- `DROP`

---

### `app_migration`

Deve possuir permissões para:

- criação de tabelas;
- alteração estrutural;
- criação de índices;
- criação de views;
- execução de migrations.

---

### `app_admin`

Deve possuir controle administrativo total sobre o banco.

---

## 3.4 Requisitos Técnicos

O projeto deverá:

- criar roles e usuários via SQL;
- aplicar permissões utilizando comandos DCL;
- utilizar grants apropriados;
- restringir privilégios desnecessários;
- demonstrar separação de responsabilidade entre execução da aplicação e manutenção estrutural do banco.

---

## 3.5 Exemplo Esperado de DCL

```sql
CREATE ROLE app_runtime;
CREATE ROLE app_migration;
CREATE ROLE app_admin;

CREATE USER runtime_user WITH PASSWORD 'senha_runtime';
CREATE USER migration_user WITH PASSWORD 'senha_migration';

GRANT app_runtime TO runtime_user;
GRANT app_migration TO migration_user;

GRANT CONNECT ON DATABASE eventos TO app_runtime;
GRANT CONNECT ON DATABASE eventos TO app_migration;

GRANT USAGE ON SCHEMA public TO app_runtime;
GRANT USAGE ON SCHEMA public TO app_migration;

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO app_runtime;
```

---

# 4. Dashboard Administrativo de Eventos

## Objetivo

Criar uma página administrativa exibindo:

- nome do evento;
- data de início;
- data de fim;
- local;
- status do evento.

---

## Status possíveis

- Encerrado
- Em andamento
- Futuro

---

## Rotas

```text
GET /admin/eventos
```

---

## Requisitos

- consulta SQL via JDBC;
- renderização Mustache;
- cálculo de status via SQL ou backend.

---

# 5. Perfil do Participante

## Objetivo

Criar página dinâmica de perfil do participante contendo:

### Dados pessoais

- nome;
- CPF;
- e-mail;
- telefone.

### Eventos inscritos

- nome;
- local;
- data.

---

## Rotas

```text
GET /participante/:cpf
```

---

# 6. Cadastro de Palestras com Múltiplos Palestrantes

## Objetivo

Permitir cadastro de palestras associadas a múltiplos palestrantes.

---

## Rotas

```text
GET /palestra/nova
POST /palestra
```

---

## Requisitos

- formulário HTML;
- seleção múltipla;
- transações JDBC;
- persistência em:
  - `palestra`
  - `palestra_palestrante`.

---

# 7. Relatório Gerencial de Palestrantes

## Objetivo

Listar palestrantes e quantidade de palestras ministradas.

---

## Rotas

```text
GET /admin/palestrantes
```

---

## Requisitos

- `GROUP BY`;
- ordenação decrescente;
- renderização Mustache.

---

# 8. Página de Eventos Disponíveis

## Objetivo

Listar eventos disponíveis para inscrição.

---

## Rotas

```text
GET /eventos/disponiveis/:cpf
POST /inscricao
```

---

## Requisitos

- impedir inscrições duplicadas;
- listar apenas eventos não encerrados;
- validações de regra de negócio;
- mensagens de sucesso/erro.

---

# 9. Modelagem com JSONB

## Objetivo

Utilizar JSONB para armazenamento flexível de informações complementares dos eventos.

---

## Estrutura sugerida

```sql
CREATE TABLE evento (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150),
    descricao TEXT,
    data_inicio DATE,
    data_fim DATE,
    local VARCHAR(200),

    detalhes JSONB DEFAULT '{}'
);
```

---

## Possíveis usos

- programação;
- links;
- contatos;
- patrocinadores;
- configurações específicas.

---

# 10. Upload de Arquivos

## Objetivo

Permitir upload de arquivos relacionados aos eventos.

---

## Rotas

```text
GET /evento/:id/upload
POST /evento/upload
```

---

## Requisitos

- upload de imagem ou PDF;
- armazenamento local ou BYTEA;
- persistência de:
  - nome;
  - tamanho;
  - tipo MIME;
  - data de envio.

---

# 11. Tecnologias Obrigatórias

- PostgreSQL
- Java
- JDBC
- Javalin
- Mustache

---

# 12. Conceitos Obrigatórios

- CRUD;
- JOINs;
- transações JDBC;
- DCL;
- JSONB;
- upload de arquivos;
- rotas dinâmicas;
- renderização server-side;
- consultas agregadas.

---

# 13. Conclusão

O projeto deverá integrar conceitos modernos de desenvolvimento backend e banco de dados, demonstrando:

- organização arquitetural;
- separação de responsabilidades;
- segurança com DCL;
- modelagem relacional;
- uso avançado de SQL;
- integração Java + PostgreSQL.

A proposta busca aproximar o desenvolvimento acadêmico de práticas utilizadas em aplicações reais, mantendo simplicidade arquitetural e foco em boas práticas.