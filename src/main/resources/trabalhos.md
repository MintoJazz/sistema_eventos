# Especificação Consolidada: Sistema de Gerenciamento de Eventos (Trabalhos 1 & 2)

## Objetivo Geral

Desenvolver e aprimorar um Sistema de Gerenciamento de Eventos utilizando Java (com Javalin, JDBC, Mustache) e PostgreSQL, implementando funcionalidades de visualização, cadastro, controle de acesso granular baseado em papéis (DCL), modelagem hierárquica de dados com herança de tabelas e uso de JSONB para flexibilidade.

---

## Tecnologias Obrigatórias

* **Backend:** `Java`
* **Framework Web:** `Javalin`
* **Persistência:** `JDBC`
* **Template Engine:** `Mustache`
* **Frontend (Estilo):** `Pico.css`
* **Banco de Dados:** `PostgreSQL` (incluindo DCL, Herança de Tabelas, JSONB)

---

## Requisitos Funcionais e de Visualização (Principalmente Trabalho 1)

### 1. Dashboard de Eventos

* **Rota:** `GET /admin/eventos`
* **Funcionalidade:** Exibir uma tabela com **todos** os eventos cadastrados.
* **Dados:** Nome, Local, Data de Início (formatada), Data de Fim (formatada), Status.
* **Lógica de Status:** Calcular dinamicamente se o evento está "Futuro", "Em Andamento" ou "Encerrado" (pode ser via `VIEW` SQL).

### 2. Perfil do Participante

* **Rota:** `GET /participante/:cpf`
* **Funcionalidade:** Exibir os detalhes de um participante específico.
* **Dados:** Informações pessoais (Nome, CPF, Email, Data Nasc. formatada) e uma tabela listando **todos os eventos** nos quais este participante está inscrito (Nome do Evento, Local, Data Início/Fim formatadas).

### 3. Relatório de Palestrantes

* **Rota:** `GET /admin/palestrantes`
* **Funcionalidade:** Exibir uma tabela com **todos** os palestrantes.
* **Dados:** Nome, CPF, Quantidade Total de Palestras ministradas.
* **Ordenação:** A lista deve ser ordenada pela quantidade de palestras em ordem **decrescente**.

### 4. Formulário de Nova Palestra

* **Rota:** `GET /palestra/nova`
* **Funcionalidade:** Exibir um formulário para cadastro de nova palestra.
* **Campos:** Nome da Palestra, Duração (horas), Data/Hora Início, Evento associado (select/dropdown), Palestrantes (seleção múltipla - checkboxes).
* **Dados:** O formulário deve ser pré-populado com a lista de Eventos e Palestrantes existentes.

### 5. Página de Inscrição

* **Rota:** `GET /eventos/disponiveis/:cpf`
* **Funcionalidade:** Exibir uma página para um participante específico se inscrever em novos eventos.
* **Dados:** Mostrar informações do participante e uma lista/tabela de eventos.
* **Lógica de Eventos:** A lista deve conter **apenas** eventos que:
    * **Não** estão encerrados (`status != 'Encerrado'`).
    * O participante (identificado pelo `:cpf`) **ainda não está inscrito**.

---

## Requisitos de Cadastro e Processamento (Principalmente Trabalho 1)

### 6. Processamento do Cadastro de Palestra

* **Rota:** `POST /palestra`
* **Funcionalidade:** Receber os dados do formulário de Nova Palestra e persistir no banco.
* **Validação:** Garantir que campos obrigatórios foram preenchidos, duração é positiva, e pelo menos um palestrante foi selecionado.
* **Persistência:** Inserir dados na tabela `palestra` e associações na tabela `palestra_palestrante`.
* **Requisito Crítico:** A operação de inserção deve ser realizada usando **transações JDBC** (`commit`/`rollback`).
* **Resposta:** Redirecionar o usuário para uma página de sucesso (ex: `/admin/palestras`) - Padrão PRG.

### 7. Processamento de Nova Inscrição

* **Rota:** `POST /inscricao`
* **Funcionalidade:** Receber o ID do participante e uma lista de IDs de eventos selecionados.
* **Validação:**
    * Verificar se o participante existe.
    * Para cada evento: verificar existência, se não está encerrado, e se o participante já não está inscrito.
* **Persistência:** Inserir um novo registro na tabela `inscricao` para cada evento válido.
* **Resposta:** Redirecionar o usuário de volta para o perfil (`/participante/:cpf`) com mensagem de status - Padrão PRG.

---

## Requisitos de Segurança e Modelagem Avançada (Trabalho 2)

### 8. Controle de Acesso com Roles (DCL)

* **Implementação:** Criar 4 roles no PostgreSQL: `app_admin`, `app_organizer`, `app_attendee`, `app_viewer`.
* **Permissões:** Configurar `GRANT`/`REVOKE` para cada role nas tabelas e sequências, seguindo **estritamente** as especificações:
    * `app_admin`: Acesso total.
    * `app_organizer`: CRUD apenas nos seus próprios eventos/anexos.
    * `app_attendee`: SELECT (eventos inscritos/arquivos), INSERT (inscrição).
    * `app_viewer`: SELECT em tudo.

### 9. Herança de Tabelas

* **Implementação:** Modelar `evento` (pai) e `evento_privado` (filha) usando `INHERITS`.
* **Estrutura:** `evento` com campos comuns, `evento_privado` com campos específicos (`senha_acesso`, `convidados JSONB`, etc.).

### 10. Uso de JSONB

* **Implementação:** Incluir uma coluna `JSONB` (ex: `detalhes`) na tabela `evento`.
* **Demonstração:** Implementar (ou demonstrar via SQL) a capacidade de **atualizar** e **consultar** dados estruturados dentro desta coluna (ex: programação, contatos, links).