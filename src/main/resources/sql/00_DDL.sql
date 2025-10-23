DROP DATABASE IF EXISTS sistema_eventos;

CREATE DATABASE sistema_eventos;

\connect sistema_eventos;

CREATE TABLE evento (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(200),
    data_inicio DATE DEFAULT CURRENT_DATE,
    data_fim DATE DEFAULT CURRENT_DATE,
    localidade TEXT
);

CREATE TABLE participante (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(200),
    data_nascimento DATE,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email TEXT UNIQUE
);

CREATE TABLE inscricao (
    id SERIAL PRIMARY KEY,
    evento_id INTEGER REFERENCES evento (id) ON DELETE CASCADE,
    participante_id INTEGER REFERENCES participante (id) ON DELETE CASCADE,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valor DECIMAL DEFAULT 0,
    pago BOOLEAN DEFAULT FALSE,
    CHECK (CAST (valor as NUMERIC (8,2)) >= 0),
    UNIQUE (evento_id, participante_id)
);

CREATE TABLE palestra (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    duracao INTEGER CHECK (duracao >= 0),
    data_hora_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    evento_id INTEGER REFERENCES evento (id) ON DELETE CASCADE
);

CREATE TABLE palestrante (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(200),
    biografia TEXT,
    cpf CHARACTER(11) NOT NULL UNIQUE
);

CREATE TABLE palestra_palestrante (
    palestra_id INTEGER REFERENCES palestra (id) ON DELETE CASCADE,
    palestrante_id INTEGER REFERENCES palestrante (id) ON DELETE CASCADE,
    PRIMARY KEY (palestra_id, palestrante_id)
)