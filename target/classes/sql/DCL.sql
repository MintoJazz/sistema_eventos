\connect sistema_eventos;

CREATE ROLE reader NOLOGIN;
GRANT CONNECT ON DATABASE sistema_eventos TO reader;
GRANT USAGE ON SCHEMA public TO reader;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO reader;

CREATE ROLE app_admin LOGIN PASSWORD '111';

GRANT CONNECT, CREATE, TEMPORARY ON DATABASE sistema_eventos TO app_admin;
GRANT USAGE, CREATE ON SCHEMA public TO app_admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO app_admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO app_admin;

CREATE ROLE app_organizer LOGIN PASSWORD '111';

GRANT reader TO app_organizer;
GRANT ALL PRIVILEGES ON TABLE evento TO app_organizer;
GRANT ALL PRIVILEGES ON TABLE evento_privado TO app_organizer;
GRANT ALL PRIVILEGES ON TABLE palestra TO app_organizer;
GRANT ALL PRIVILEGES ON TABLE palestra_palestrante TO app_organizer;
GRANT ALL PRIVILEGES ON TABLE inscricao TO app_organizer;
GRANT USAGE, SELECT ON SEQUENCE inscricao_id_seq TO app_organizer;

CREATE ROLE app_attendee LOGIN PASSWORD '111';
GRANT reader TO app_attendee;
GRANT ALL PRIVILEGES ON TABLE inscricao TO app_attendee;
GRANT ALL PRIVILEGES ON TABLE palestra_palestrante TO app_attendee;
GRANT UPDATE ON TABLE perfil_palestrante TO app_attendee;
GRANT INSERT ON TABLE evento TO app_attendee;
GRANT INSERT ON TABLE evento_privado TO app_attendee;
GRANT USAGE, SELECT ON SEQUENCE inscricao_id_seq TO app_attendee;
GRANT USAGE, SELECT ON SEQUENCE evento_id_seq TO app_attendee;
GRANT USAGE, SELECT ON SEQUENCE evento_id_seq TO app_attendee;

CREATE ROLE app_viewer LOGIN PASSWORD '111';

GRANT reader TO app_viewer;