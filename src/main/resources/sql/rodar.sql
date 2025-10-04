\ c sistema_eventos;
SELECT e.*
FROM inscricao i
    INNER JOIN evento e ON i.evento_id = e.id
WHERE i.participante_id = 1;

