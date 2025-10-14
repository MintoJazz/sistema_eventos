CREATE VIEW dashboard_eventos AS SELECT
    *,
    CASE
        WHEN data_inicio > CURRENT_DATE THEN 'Futuro'
        WHEN data_fim >= CURRENT_DATE THEN 'Em Andamento'
        ELSE 'Encerrado'
    END AS status_evento
FROM
    evento;