
DELETE FROM palestra_palestrante;
DELETE FROM inscricao;
DELETE FROM palestra;
DELETE FROM palestrante;
DELETE FROM participante;
DELETE FROM evento;

ALTER SEQUENCE evento_id_seq RESTART WITH 1;
ALTER SEQUENCE participante_id_seq RESTART WITH 1;
ALTER SEQUENCE palestrante_id_seq RESTART WITH 1;
ALTER SEQUENCE palestra_id_seq RESTART WITH 1;
ALTER SEQUENCE inscricao_id_seq RESTART WITH 1;

INSERT INTO evento (nome, data_inicio, data_fim, localidade)
VALUES (
        'Tech Conference 2025',
        '2025-10-20',
        '2025-10-22',
        'São Paulo'
    ),
    (
        'Data Science Summit',
        CURRENT_DATE - INTERVAL '1 day',
        CURRENT_DATE + INTERVAL '1 day',
        'Remoto'
    ),
    -- Em andamento
    (
        'Workshop de Inovação',
        '2025-08-15',
        '2025-08-16',
        'Rio de Janeiro'
    ),
    (
        'Festival de Cultura Geek',
        '2025-11-05',
        '2025-11-10',
        'Belo Horizonte'
    ),
    -- Duração > 3 dias
    (
        'Encontro de Desenvolvedores',
        '2025-01-10',
        '2025-01-12',
        'Curitiba'
    ),
    (
        'Semana da Inteligência Artificial',
        '2025-12-01',
        '2025-12-05',
        'São Paulo'
    ),
    (
        'Evento Sem Palestras',
        '2026-02-01',
        '2026-02-02',
        'Porto Alegre'
    ),
    (
        'Cybersecurity Nexus 2025',
        '2025-11-25',
        '2025-11-27',
        'Salvador'
    ),
    (
        'Encontro UX/UI Design',
        '2025-09-10',
        '2025-09-11',
        'Remoto'
    ),
    -- Evento recém-encerrado
    (
        'Fórum de Blockchain',
        '2026-03-15',
        '2026-03-17',
        'Recife'
    ),
    (
        'Evento Vazio 2026',
        '2026-05-20',
        '2026-05-21',
        'Florianópolis'
    );

INSERT INTO participante (nome, data_nascimento, cpf, email)
VALUES (
        'Carlos Silva',
        '1990-05-15',
        '11122233344',
        'carlos.silva@email.com'
    ),
    (
        'Ana Pereira',
        '1992-09-20',
        '22233344455',
        'ana.pereira@email.com'
    ),
    (
        'Bruno Costa',
        '1988-03-10',
        '33344455566',
        'bruno.costa@email.com'
    ),
    (
        'Mariana Lima',
        '1995-11-30',
        '44455566677',
        'mariana.lima@email.com'
    ),
    (
        'Lucas Almeida',
        '2000-01-25',
        '55566677788',
        'lucas.almeida@email.com'
    ),
    (
        'Juliana Santos',
        '1998-07-07',
        '66677788899',
        'juliana.santos@email.com'
    ),
    (
        'Fernando Oliveira',
        '1985-12-12',
        '77788899900',
        'fernando.o@email.com'
    ),
    (
        'Rafael Martins',
        '1993-02-18',
        '88899900011',
        'rafael.martins@email.com'
    ),
    (
        'Larissa Ferreira',
        '1999-06-22',
        '99900011122',
        'larissa.f@email.com'
    ),
    (
        'Thiago Gomes',
        '1987-10-05',
        '00011122233',
        'thiago.gomes@email.com'
    );

INSERT INTO palestrante (nome, biografia, cpf)
VALUES (
        'Dra. Evelyn Rodrigues',
        'Especialista em Inteligência Artificial e Machine Learning.',
        '12345678901'
    ),
    (
        'Prof. Marcos Andrade',
        'Autor de best-sellers sobre desenvolvimento ágil.',
        '23456789012'
    ),
    (
        'Carlos Silva',
        'Desenvolvedor Sênior e entusiasta de DevOps.',
        '11122233344'
    ),
    (
        'Beatriz Souza',
        'PhD em Ciência de Dados com foco em visualização.',
        '45678901234'
    ),
    (
        'Ricardo Rocha',
        'Consultor de segurança da informação.',
        '56789012345'
    ),
    (
        'Dra. Sofia Mendes',
        'Especialista em segurança cibernética e privacidade de dados.',
        '67890123456'
    ),
    (
        'Paulo Costa',
        'Designer de produto com mais de 10 anos de experiência em UX/UI.',
        '78901234567'
    );

INSERT INTO palestra (nome, duracao, data_hora_inicio, evento_id)
VALUES (
        'O Futuro da Tecnologia Quântica',
        90,
        '2025-10-20 10:00:00',
        1
    ),
    (
        'Big Data e Analytics na Prática',
        120,
        '2025-10-20 14:00:00',
        1
    ),
    (
        'Inovação em Modelos de Negócio',
        60,
        '2025-08-15 09:00:00',
        3
    ),
    (
        'Introdução ao SQL para Dados',
        180,
        (
            SELECT data_inicio
            FROM evento
            WHERE id = 2
        ),
        2
    ),
    (
        'Desenvolvimento de APIs com Javalin',
        90,
        (
            SELECT data_inicio
            FROM evento
            WHERE id = 2
        ),
        2
    ),
    (
        'Segurança em Aplicações Web',
        75,
        '2025-10-21 11:00:00',
        1
    ),
    (
        'Carreira em TI: Além do Código',
        45,
        '2025-11-06 16:00:00',
        4
    ),
    (
        'Defesa Cibernética Ativa',
        90,
        '2025-11-25 10:00:00',
        8
    ),
    (
        'Princípios de Design Centrado no Usuário',
        75,
        '2025-09-10 09:30:00',
        9
    ),
    (
        'O Futuro das Criptomoedas',
        120,
        '2026-03-15 14:00:00',
        10
    ),
    (
        'Workshop de Figma para Iniciantes',
        180,
        '2025-09-11 13:00:00',
        9
    ),
    (
        'Tópicos Avançados em Blockchain',
        90,
        '2026-03-16 11:00:00',
        10
    );

INSERT INTO inscricao (evento_id, participante_id, valor, pago)
VALUES -- Carlos Silva (participante 1) está em MUITOS eventos
    (1, 1, 150.00, TRUE),
    (2, 1, 200.00, TRUE),
    (3, 1, 100.00, TRUE),
    (4, 1, 50.00, TRUE),
    (9, 1, 80.00, TRUE),
    -- Ana Pereira (participante 2)
    (1, 2, 150.00, TRUE),
    (8, 2, 250.00, TRUE),
    (6, 2, 350.00, FALSE),
    -- Bruno Costa (participante 3)
    (2, 3, 200.00, TRUE),
    (3, 3, 100.00, FALSE),
    (1, 3, 150.00, TRUE),
    (10, 3, 300.00, TRUE),
    -- Lucas Almeida (participante 5)
    (2, 5, 200.00, TRUE),
    (4, 5, 50.00, TRUE),
    -- Juliana Santos (participante 6)
    (4, 6, 50.00, TRUE),
    (9, 6, 80.00, FALSE),
    -- Fernando Oliveira (participante 7)
    (6, 7, 350.00, TRUE),
    (8, 7, 250.00, TRUE),
    -- Rafael Martins (participante 8)
    (8, 8, 250.00, TRUE),
    (10, 8, 300.00, TRUE),
    (1, 8, 150.00, TRUE),
    -- Larissa Ferreira (participante 9)
    (9, 9, 80.00, TRUE),
    (2, 9, 200.00, TRUE),
    -- Thiago Gomes (participante 10)
    (1, 10, 150.00, FALSE),
    (5, 10, 120.00, TRUE);

INSERT INTO palestra_palestrante (palestra_id, palestrante_id)
VALUES -- Dra. Evelyn (palestrante 1) é especialista em IA e Dados
    (1, 1),
    (2, 1),
    -- Prof. Marcos (palestrante 2) foca em gestão e inovação
    (3, 2),
    -- Carlos Silva (palestrante 3, também participante) é desenvolvedor
    (4, 3),
    (5, 3),
    (11, 3),
    -- Beatriz Souza (palestrante 4) é a mais ativa em palestras
    (2, 4),
    (4, 4),
    (6, 4),
    (1, 4),
    (5, 4),
    -- Dra. Sofia (palestrante 6) é especialista em segurança
    (6, 6),
    (8, 6),
    -- Paulo Costa (palestrante 7) é de UX/UI
    (9, 7),
    (11, 7),
    -- Adicionando mais palestrantes a palestras existentes
    (3, 1),
    -- Dra. Evelyn também fala de inovação
    (10, 4);