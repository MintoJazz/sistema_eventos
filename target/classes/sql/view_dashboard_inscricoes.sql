CREATE OR REPLACE VIEW dashboard_inscricoes AS
SELECT
    i.id AS inscricao_id,
    i.valor,
    i.data_hora,
    i.pago,
    
    -- Colunas do Evento (já com status da outra VIEW)
    e.id AS evento_id,
    e.nome AS evento_nome,
    e.data_inicio,
    e.data_fim,
    e.localidade,
    e.status_evento,

    -- Colunas do Participante
    p.id AS participante_id,
    p.nome AS participante_nome,
    p.cpf,
    p.email,
    p.data_nascimento
FROM
    inscricao i
-- Junta com a VIEW de eventos para já pegar o status calculado
JOIN
    dashboard_eventos e ON i.evento_id = e.id
-- Junta com a tabela de participantes
JOIN
    participante p ON i.participante_id = p.id
ORDER BY
    i.data_hora DESC; -- Ordena pelas inscrições mais recentes primeiro