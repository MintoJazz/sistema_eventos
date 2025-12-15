\connect sistema_eventos;

DELETE FROM palestra_palestrante;
DELETE FROM inscricao;
DELETE FROM palestra;
DELETE FROM evento_privado;
DELETE FROM evento;
DELETE FROM perfil_palestrante;
DELETE FROM usuario;

ALTER SEQUENCE usuario_id_seq RESTART WITH 1;
ALTER SEQUENCE evento_id_seq RESTART WITH 1;
ALTER SEQUENCE palestra_id_seq RESTART WITH 1;
ALTER SEQUENCE inscricao_id_seq RESTART WITH 1;

INSERT INTO usuario (nome, cpf, email, senha, cargo, data_nascimento) VALUES 
('Igor Ávila', '11111111111', 'igor.avila@riogrande.ifrs.edu.br', 'admin123', 'ADMIN', '1980-01-01');

INSERT INTO usuario (nome, cpf, email, senha, cargo, data_nascimento) VALUES 
('Márcio Josué', '22222222222', 'marcio.josue@riogrande.ifrs.edu.br', 'viewer123', 'VIEWER', '1975-05-20');

INSERT INTO usuario (nome, cpf, email, senha, cargo, data_nascimento) VALUES 
('Vinícius Fritzen', '33333333333', 'fritzenvinicius@gmail.com', 'poo2025', 'COMUM', '1985-03-15'),
('Narusci dos Santos', '44444444444', 'narusci.bastos@riogrande.ifrs.edu.br', 'sistemas', 'COMUM', '1982-07-10'),
('Luciano Vargas', '55555555555', 'luciano.vargas@riogrande.ifrs.edu.br', 'fundamentos', 'COMUM', '1978-11-30'),
('Antonio Cesar', '66666666666', 'antonio.cesar@riogrande.ifrs.edu.br', 'matematica', 'COMUM', '1960-01-20'),
('Denise de Souza', '77777777777', 'denise.souza@riogrande.ifrs.edu.br', 'sociedade', 'COMUM', '1988-09-12'),
('Cleber Schroeder', '88888888888', 'cleber.schroeder@riogrande.ifrs.edu.br', 'engsoft', 'COMUM', '1983-04-25'),
('Javier Garcia', '99999999999', 'javier.garcia@riogrande.ifrs.edu.br', 'discreta', 'COMUM', '1970-12-05'),
('Tiago Lopes', '10101010101', 'tiago.lopes@riogrande.ifrs.edu.br', 'design', 'COMUM', '1986-06-18'),
('Eduardo Brião', '12121212121', 'eduardo.briao@riogrande.ifrs.edu.br', 'arquitetura', 'COMUM', '1981-02-14'),
('Luis Henrique', '13131313131', 'luis.henrique@riogrande.ifrs.edu.br', 'estatistica', 'COMUM', '1979-08-22'),
('Ana Carolina Salles', '14141414141', 'ana.salles@riogrande.ifrs.edu.br', 'empreenda', 'COMUM', '1990-10-30'),
('Marcia Madeira', '15151515151', 'marcia.madeira@riogrande.ifrs.edu.br', 'metodologia', 'COMUM', '1976-05-05'),
('Raquel de Miranda', '16161616161', 'raquel.miranda@riogrande.ifrs.edu.br', 'seminarios', 'COMUM', '1984-01-15'),
('Lucía Silveira', '17171717171', 'lucia.silveira@riogrande.ifrs.edu.br', 'english', 'COMUM', '1980-03-08'),
('Crisiane de Freitas', '18181818181', 'crisiane.freitas@riogrande.ifrs.edu.br', 'libras', 'COMUM', '1987-11-20');

INSERT INTO perfil_palestrante (usuario_id, biografia) VALUES 
(1, 'Coordenador do curso TADS. Especialista em Banco de Dados e Padrões de Projeto.'),
(2, 'Direção de Ensino. Especialista em Arquitetura de Software e Programação Modular.'),
(3, 'Mestre. Ministra POO e Lógica de Programação.'),
(4, 'Especialista em Sistemas de Informação e IHC.'),
(5, 'Professor de Fundamentos da Computação e Redes.'),
(6, 'Professor de Matemática Básica.'),
(7, 'Professora de Tecnologia e Sociedade.'),
(8, 'Especialista em Engenharia de Software e Desenvolvimento Web.'),
(9, 'Doutor. Ministra Matemática Discreta.'),
(10, 'Especialista em Design de Interface e Interação.'),
(11, 'Professor de Organização de Computadores e SO.'),
(12, 'Professor de Estatística e Inferência.'),
(13, 'Professora de Empreendedorismo.'),
(14, 'Professora de Metodologia Científica.'),
(15, 'Responsável por Seminários e Tópicos Avançados.'),
(16, 'Professora de Inglês Técnico.'),
(17, 'Professora de Libras.');

INSERT INTO usuario (nome, cpf, email, senha, cargo, data_nascimento) VALUES 
('Carlos Alberto Xavier Teixeira Junior', '20240186770', 'teixeirahist1988@gmail.com', 'aluno123', 'COMUM', '1988-01-01'),
('Francine Gonçalves Nunes Moraes', '20240187480', 'francinegnmoraes@gmail.com', 'aluno123', 'COMUM', '1995-05-10'),
('Gabriel Jacinto da Silva', '20240071810', 'ggabrieljs@gmail.com', 'aluno123', 'COMUM', '2000-02-20'),
('Janaiton Rodrigues Mena', '20240129960', 'janaitonmena@gmail.com', 'aluno123', 'COMUM', '1999-07-15'),
('Jorge Antonio Alves Rodrigues', '20240187930', 'jorge.alves.rodriguez@gmail.com', 'aluno123', 'COMUM', '1998-11-30'),
('Matheus Azevedo de Lima', '20230039650', 'mth91749823@gmail.com', 'aluno123', 'COMUM', '2001-03-25'),
('Mathias Pereira Cardozo de Aguiar', '20230100840', 'mathiasaguiar23@gmail.com', 'aluno123', 'COMUM', '2002-08-14'),
('Nata Jardim Costa', '20240188550', 'costanata00@gmail.com', 'aluno123', 'COMUM', '2003-01-10'),
('Roberto Fabiano Cunha da Silva', '20240189620', 'robertocunha.silva@gmail.com', 'aluno123', 'COMUM', '1990-12-12'),
('Domingos Savio Ximendes Martins Júnior', '20230178530', 'domingos.mar.jr@outlook.com', 'aluno123', 'COMUM', '1997-04-05'),
('Gabriel de Farias Borges', '20240190100', 'gabrielfborges04@gmail.com', 'aluno123', 'COMUM', '2004-06-18'),
('Jaaziel Pinto Machado', '20240187660', 'jaazielpm.jpm@gmail.com', 'aluno123', 'COMUM', '1996-09-22'),
('Joao Vitor Bernadotte Pinho', '20240190390', 'joaobernandote2012@gmail.com', 'aluno123', 'COMUM', '2002-10-05'),
('Lauana Cabreira Simoes', '20240072700', 'lauanacabreirasimoesc@gmail.com', 'aluno123', 'COMUM', '2001-12-25'),
('Matheus Constantino Aguiar', '20230039380', '2023003938@aluno.riogrande.ifrs.edu.br', 'aluno123', 'COMUM', '2003-05-30'),
('Murilo da Fonseca Portela', '20240073050', 'muriloportelala@gmail.com', 'aluno123', 'COMUM', '2002-02-02'),
('Ricardo Freitas Vergara', '20230040500', 'ricardo.vergara@riogrande.ifrs.edu.br', 'aluno123', 'COMUM', '1995-08-15'),
('Tiago Carreiro Gomes', '20230179150', 'tiago.c.gomes@hotmail.com', 'aluno123', 'COMUM', '1999-01-20'),
('Alessandro Fernandes da Silva Filho', '20240189530', 'alessandrofernandess125@gmail.com', 'aluno123', 'COMUM', '2000-09-09'),
('Arthur Pinheiro Gasque', '20240190200', 'arthurpg26@gmail.com', 'aluno123', 'COMUM', '2004-11-11'),
('Deise Mara da Silva Neves', '20240130260', 'deise475@gmail.com', 'aluno123', 'COMUM', '1992-06-06'),
('Everton Luis de Almeida Porciuncula', '20240187010', 'evertonfurg@gmail.com', 'aluno123', 'COMUM', '1985-07-07'),
('Jean Carlo da Silva Carvalho', '20240187750', 'jeancarlo.carvalho@hotmail.com', 'aluno123', 'COMUM', '1993-03-03'),
('João Vitor Miranda Freitas', '20240187840', 'vitorfreitas1103@gmail.com', 'aluno123', 'COMUM', '2002-04-04'),
('Lineker Lauriente Vahl', '20240188370', 'linekervahl@gmail.com', 'aluno123', 'COMUM', '1998-05-05'),
('Anderson Duarte Pereira', '20240186210', 'anderson.dpereira85@gmail.com', 'aluno123', 'COMUM', '1985-10-10'),
('Erick Freitas Orrico', '20240186950', 'orricoerick@gmail.com', 'aluno123', 'COMUM', '2001-01-01'),
('Fany Botelho da Costa', '20240130530', 'botelho.fany@gmail.com', 'aluno123', 'COMUM', '1997-07-17'),
('Jailton Luis Padilha de Oliveira', '20210067990', 'jalupadilha2@gmail.com', 'aluno123', 'COMUM', '1990-08-08'),
('Pablo Alcalde de Oliveira', '20240188640', 'pabloalcalde29@gmail.com', 'aluno123', 'COMUM', '2003-09-29'),
('Vítor Luiz Dias de Lima', '20240189440', 'admvitordias@gmail.com', 'aluno123', 'COMUM', '2000-12-12'),
('Iuri Rodrigues Seifriz', '20240072250', 'iurirseifriz@gmail.com', 'aluno123', 'COMUM', '2001-11-11'),
('John Lennon da Silva Souza', '20240072340', 'johnlsouzal@gmail.com', 'aluno123', 'COMUM', '1995-12-08'),
('Maxsuel Acosta da Costa', '20230163820', 'maxsuel.uix@gmail.com', 'aluno123', 'COMUM', '1999-02-14'),
('Christian dos Santos Torres', '20240071540', 'christian.ds.torres@gmail.com', 'aluno123', 'COMUM', '2002-06-20'),
('Fernando Liborio Queiroz Vasques Neto', '20240187100', 'fflqvn@gmail.com', 'aluno123', 'COMUM', '2000-05-05'),
('Muriel Correia Brum', '20240132310', 'murielcorreia92@gmail.com', 'aluno123', 'COMUM', '1992-04-20'),
('Nilton Nei Justino Alonso', '20220102300', 'nilton.alonso10@gmail.com', 'aluno123', 'COMUM', '1980-08-30'),
('Walber Coelho de Anastácio', '20220093470', 'walbercanastacio@hotmail.com', 'aluno123', 'COMUM', '1994-09-15');

INSERT INTO evento (nome, data_inicio, data_fim, localidade, organizador) VALUES 
('Lógica de Programação', '2025-02-20', '2025-07-20', 'LAB1', 3),
('Sistemas de Informação', '2025-02-20', '2025-07-20', 'LAB1', 4),
('Fundamentos da Computação', '2025-02-20', '2025-07-20', 'LAB1', 5),
('Matemática Básica', '2025-02-20', '2025-07-20', 'Sala 406', 6),
('Tecnologia e Sociedade', '2025-02-20', '2025-07-20', 'Sala 408', 7),
('Interação Humano Computador', '2025-02-20', '2025-07-20', 'LAB1', 4),
('Engenharia de Software', '2025-02-20', '2025-07-20', 'LAB3', 8),
('Fundamentos de Matemática Discreta', '2025-02-20', '2025-07-20', 'Sala 408', 9),
('Design de Interface', '2025-02-20', '2025-07-20', 'LAB3', 10),
('Projeto e Modelagem de Bancos de Dados', '2025-02-20', '2025-07-20', 'LAB3', 1),
('Organização de Computadores', '2025-02-20', '2025-07-20', 'LAB3', 11),
('Programação Modular', '2025-02-20', '2025-07-20', 'LAB3', 2),
('Design de Interação', '2025-02-20', '2025-07-20', 'LAB8', 10),
('Implementação e Operação de Banco de Dados', '2025-02-20', '2025-07-20', 'LAB3', 1),
('Análise e Projeto de Software', '2025-02-20', '2025-07-20', 'LAB5', 4),
('Sistemas Operacionais', '2025-02-20', '2025-07-20', 'LAB5', 11),
('Programação Orientada a Objetos', '2025-02-20', '2025-07-20', 'LAB5', 3),
('Fund. Inferência', '2025-02-20', '2025-07-20', 'LAB5', 12),
('Desenvolvimento Aplicativo WEB I', '2025-02-20', '2025-07-20', 'LAB6', 8),
('Princípios e Padrões de Projeto', '2025-02-20', '2025-07-20', 'LAB7', 1),
('Gerência de Projetos', '2025-02-20', '2025-07-20', 'LAB7', 4),
('Estrutura de Dados', '2025-02-20', '2025-07-20', 'LAB7', 5),
('Princípios e Padrões de Arquitetura', '2025-02-20', '2025-07-20', 'LAB7', 2),
('Desenvolvimento Aplicativos WEB II', '2025-02-20', '2025-07-20', 'LAB8', 3),
('Empreendimentos Empresariais', '2025-02-20', '2025-07-20', 'LAB8', 13),
('Redes de Computadores', '2025-02-20', '2025-07-20', 'LAB2', 5),
('Qualidade de Software', '2025-02-20', '2025-07-20', 'LAB8', 4),
('Metodologia Científica', '2025-02-20', '2025-07-20', 'LAB8', 14);

INSERT INTO palestra (nome, duracao, data_hora_inicio, evento_id) VALUES 
('Aula 01: Introdução a Objetos', 100, '2025-02-20 18:50:00', 17),
('Aula 01: Modelo Relacional', 100, '2025-02-21 18:50:00', 10),
('Aula 01: Requisitos de Software', 100, '2025-02-22 18:50:00', 7),
('Aula 01: Lógica Proposicional', 100, '2025-02-23 18:50:00', 8),
('Aula 01: HTML e CSS Básico', 100, '2025-02-24 18:50:00', 20),
('Aula 01: Ponteiros em C', 100, '2025-02-25 18:50:00', 23),
('Workshop: Design Thinking', 120, '2025-03-10 19:00:00', 13),
('Seminário: IA na Sociedade', 90, '2025-04-05 20:00:00', 5),
('Painel Integrador: Arquitetura, Banco e Código', 180, '2025-06-01 19:00:00', 17);

INSERT INTO palestra_palestrante (palestra_id, perfil_palestrante_id) VALUES 
(1, 3),
(2, 1),
(3, 8),
(4, 9),
(5, 8),
(6, 5),
(7, 10),
(8, 7),
(9, 3),
(9, 1),
(9, 2);

INSERT INTO inscricao (evento_id, usuario_id, valor, pago) VALUES 
(17, 18, 0.00, TRUE),
(17, 19, 0.00, TRUE),
(17, 20, 0.00, TRUE),
(17, 21, 0.00, TRUE),
(17, 22, 0.00, TRUE),
(17, 23, 0.00, TRUE),
(17, 24, 0.00, TRUE),
(17, 25, 0.00, TRUE),
(17, 26, 0.00, TRUE),
(17, 32, 0.00, TRUE),
(8, 18, 0.00, TRUE),
(8, 19, 0.00, TRUE),
(8, 25, 0.00, TRUE),
(8, 32, 0.00, TRUE),
(8, 49, 0.00, TRUE),
(8, 50, 0.00, TRUE),
(8, 51, 0.00, TRUE),
(8, 52, 0.00, TRUE),
(2, 19, 0.00, TRUE),
(2, 29, 0.00, TRUE),
(2, 36, 0.00, TRUE),
(2, 37, 0.00, TRUE),
(2, 38, 0.00, TRUE),
(2, 39, 0.00, TRUE),
(2, 40, 0.00, TRUE),
(2, 41, 0.00, TRUE),
(2, 42, 0.00, TRUE);

-- =================================================================== --
-- ADICIONANDO O CHURRASCO (EVENTO PRIVADO)
-- Com JSONB estruturado (ID e Nome)
-- =================================================================== --
INSERT INTO evento_privado (nome, data_inicio, data_fim, localidade, organizador, senha_acesso, convidados) VALUES 
('Churrasco da Turma TADS', '2025-12-20', '2025-12-20', 'Ginásio do IFRS', 1, 'picanha2025', 
'[
    {"id": 29, "nome": "Jaaziel Pinto Machado"},
    {"id": 25, "nome": "Nata Jardim Costa"},
    {"id": 34, "nome": "Ricardo Freitas Vergara"},
    {"id": 26, "nome": "Roberto Fabiano Cunha da Silva"},
    {"id": 22, "nome": "Jorge Antonio Alves Rodrigues"},
    {"id": 2,  "nome": "Márcio Josué"},
    {"id": 1,  "nome": "Igor Ávila"},
    {"id": 3,  "nome": "Vinícius Fritzen"},
    {"id": 4,  "nome": "Narusci dos Santos"}
]');

\connect postgres;