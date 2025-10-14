CREATE OR REPLACE VIEW dashboard_palestrantes AS SELECT
    p.*,
    COALESCE(i.qtd_palestras,0) AS nro_palestras
FROM
    palestrante p
LEFT JOIN (
    SELECT
        pp.palestrante_id,
        COUNT(pp.palestra_id) as qtd_palestras
    FROM
        palestra_palestrante pp
    GROUP BY
        pp.palestrante_id
) i ON
    i.palestrante_id = p.id
ORDER BY
    nro_palestras DESC NULLS LAST;