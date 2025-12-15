DROP DATABASE IF EXISTS sistema_eventos;
CREATE DATABASE sistema_eventos;

\connect sistema_eventos;

CREATE TYPE user_role AS ENUM (
    'ADMIN', 
    'VIEWER',
    'COMUM'
);

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cargo user_role NOT NULL DEFAULT 'COMUM',
    
    data_nascimento DATE
);

CREATE TABLE perfil_palestrante (
    usuario_id INTEGER PRIMARY KEY REFERENCES usuario(id) ON DELETE CASCADE,
    biografia TEXT
);

CREATE TABLE evento (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(200),
    data_inicio DATE DEFAULT CURRENT_DATE,
    data_fim DATE DEFAULT CURRENT_DATE,
    localidade TEXT,
    organizador INTEGER REFERENCES usuario(id) ON DELETE CASCADE,
    detalhes JSONB DEFAULT '{}' -- <--- COLUNA NOVA ADICIONADA AQUI
);

CREATE TABLE evento_privado (
    senha_acesso VARCHAR(100),
    convidados JSONB DEFAULT '[]',
    CONSTRAINT evento_privado_pkey PRIMARY KEY (id)
) INHERITS (evento);

CREATE TABLE palestra (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    duracao INTEGER CHECK (duracao >= 0),
    data_hora_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    evento_id INTEGER REFERENCES evento (id) ON DELETE CASCADE,
    evento_privado_id INTEGER REFERENCES evento_privado (id) ON DELETE CASCADE,

    CONSTRAINT check_origem_palestra CHECK (
        (evento_id IS NOT NULL AND evento_privado_id IS NULL) 
        OR 
        (evento_id IS NULL AND evento_privado_id IS NOT NULL)
    )
);

CREATE TABLE palestra_palestrante (
    palestra_id INTEGER REFERENCES palestra (id) ON DELETE CASCADE,
    perfil_palestrante_id INTEGER REFERENCES perfil_palestrante (usuario_id) ON DELETE CASCADE,
    PRIMARY KEY (palestra_id, perfil_palestrante_id)
);

CREATE TABLE inscricao (
    id SERIAL PRIMARY KEY,
    
    usuario_id INTEGER REFERENCES usuario(id) ON DELETE CASCADE,
    
    evento_id INTEGER REFERENCES evento (id) ON DELETE CASCADE,
    evento_privado_id INTEGER REFERENCES evento_privado (id) ON DELETE CASCADE,
    
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valor DECIMAL DEFAULT 0,
    pago BOOLEAN DEFAULT FALSE, 
    credencial BYTEA,

    CHECK (CAST (valor as NUMERIC (8,2)) >= 0),

    CONSTRAINT check_origem_inscricao CHECK (
        (evento_id IS NOT NULL AND evento_privado_id IS NULL) 
        OR 
        (evento_id IS NULL AND evento_privado_id IS NOT NULL)
    ),

    UNIQUE (evento_id, usuario_id),
    UNIQUE (evento_privado_id, usuario_id)
);

CREATE OR REPLACE VIEW view_palestrante AS
SELECT 
    u.id, 
    u.nome, 
    u.cpf,
    u.email, 
    pp.biografia,
    (SELECT COUNT(*) FROM palestra_palestrante link WHERE link.perfil_palestrante_id = u.id) AS quantidade_palestras
FROM usuario u 
JOIN perfil_palestrante pp ON u.id = pp.usuario_id;

CREATE OR REPLACE VIEW view_inscritos AS
SELECT 
    i.id AS inscricao_id,
    u.nome AS nome_participante,
    u.email AS email_participante,
    COALESCE(e.nome, ep.nome) AS nome_evento,
    i.pago
FROM inscricao i
JOIN usuario u ON i.usuario_id = u.id
LEFT JOIN evento e ON i.evento_id = e.id
LEFT JOIN evento_privado ep ON i.evento_privado_id = ep.id;

\connect postgres;