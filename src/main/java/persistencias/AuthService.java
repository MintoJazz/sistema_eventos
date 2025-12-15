package persistencias;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {

    public static boolean isAllowed(Connection conexao, String tabela, String acao) throws SQLException {
        System.out.println("[DEBUG] AuthService.isAllowed(Connection, Tabela=" + tabela + ", Acao=" + acao + ")");
        
        String sql = "SELECT current_user, has_table_privilege(current_user, ?, ?)";
        
        try (PreparedStatement preparedStatement = conexao.prepareStatement(sql)) {
            preparedStatement.setString(1, tabela);
            preparedStatement.setString(2, acao.toUpperCase());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    boolean allowed = resultSet.getBoolean(2);
                    System.out.println("[DEBUG] -> Resultado: " + allowed + " (User: " + resultSet.getString(1) + ")");
                    return allowed;
                }
            }
        } 
        System.out.println("[DEBUG] -> Resultado: false (Erro SQL)");
        return false;
    }

    public static boolean isAllowed(String role, String tabela, String acao) {
        System.out.println("[DEBUG] AuthService.isAllowed(Role=" + role + ", Tabela=" + tabela + ", Acao=" + acao + ")");
        try (Connection conexao = new Conexao().getConexao(role)) {
            return isAllowed(conexao, tabela, acao);
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return false;
    }

    public static boolean isParticipante(String idUser, String idEvento) {
        System.out.println("[DEBUG] AuthService.isParticipante(User=" + idUser + ", Evento=" + idEvento + ")");
        try (Connection conexao = new Conexao().getConexao("app_admin")) {
            // Nota: Corrigido o nome da coluna para 'usuario_id' conforme novo DDL
            try (PreparedStatement preparedStatement = conexao.prepareStatement(
                """
                SELECT 1 FROM inscricao 
                WHERE usuario_id = ? 
                    AND (evento_id = ? OR evento_privado_id = ?)
                """
            )) {
                // Conversão segura para evitar erro se idUser for nulo/vazio
                int uId = (idUser == null || idUser.isEmpty()) ? 0 : Integer.parseInt(idUser);
                int eId = (idEvento == null || idEvento.isEmpty()) ? 0 : Integer.parseInt(idEvento);

                preparedStatement.setInt(1, uId);
                preparedStatement.setInt(2, eId);
                preparedStatement.setInt(3, eId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    boolean result = resultSet.next();
                    System.out.println("[DEBUG] -> Resultado: " + result);
                    return result;
                }
            }
        } catch (Exception e) { // Catch genérico para pegar NumberFormat também
            System.err.println("[DEBUG] Erro em isParticipante: " + e.getMessage());
            // throw new RuntimeException("Falha na conexão: " + e.getMessage(), e); // Opcional: comentar para não quebrar fluxo de teste
            return false;
        } 
    }

    public static boolean isPalestrante(String idUser, String idPalestra) {
        System.out.println("[DEBUG] AuthService.isPalestrante(User=" + idUser + ", Palestra=" + idPalestra + ")");
        try (Connection conexao = new Conexao().getConexao("app_admin")) {
            // Nota: Corrigido 'perfil_palestrante_id' para 'usuario_id' se seu DDL mudou, 
            // mas mantendo original pois DDL usa 'perfil_palestrante_id' na fk
            try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT 1 FROM palestra_palestrante WHERE perfil_palestrante_id = ? AND palestra_id = ?")) {
                
                int uId = (idUser == null || idUser.isEmpty()) ? 0 : Integer.parseInt(idUser);
                int pId = (idPalestra == null || idPalestra.isEmpty()) ? 0 : Integer.parseInt(idPalestra);

                preparedStatement.setInt(1, uId);
                preparedStatement.setInt(2, pId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    boolean result = resultSet.next();
                    System.out.println("[DEBUG] -> Resultado: " + result);
                    return result;
                }
            }
        } catch (Exception e) {
            System.err.println("[DEBUG] Erro em isPalestrante: " + e.getMessage());
            return false;
        } 
    }

    public static boolean isOrganizer(String idUser, String idEvento) {
        System.out.println("[DEBUG] AuthService.isOrganizer(User=" + idUser + ", Evento=" + idEvento + ")");
        try (Connection conexao = new Conexao().getConexao("app_admin")) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT 1 FROM evento WHERE id = ? AND organizador = ?")) {
                
                int uId = (idUser == null || idUser.isEmpty()) ? 0 : Integer.parseInt(idUser);
                int eId = (idEvento == null || idEvento.isEmpty()) ? 0 : Integer.parseInt(idEvento);

                preparedStatement.setInt(1, eId);
                preparedStatement.setInt(2, uId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    boolean result = resultSet.next();
                    System.out.println("[DEBUG] -> Resultado: " + result);
                    return result;
                }
            }
        } catch (Exception e) {
            System.err.println("[DEBUG] Erro em isOrganizer: " + e.getMessage());
            return false;
        }
    }

    public static boolean isOrganizerPalestra(String idPalestra, String idUser) {
        System.out.println("[DEBUG] AuthService.isOrganizerPalestra(Palestra=" + idPalestra + ", User=" + idUser + ")");
        try (Connection conexao = new Conexao().getConexao("app_admin")) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement(
                """
                SELECT 1 
                FROM palestra p 
                JOIN evento e ON (e.id = p.evento_id OR e.id = p.evento_privado_id)
                WHERE p.id = ? AND e.organizador = ?
                """
            )) {
                int pId = (idPalestra == null || idPalestra.isEmpty()) ? 0 : Integer.parseInt(idPalestra);
                int uId = (idUser == null || idUser.isEmpty()) ? 0 : Integer.parseInt(idUser);

                preparedStatement.setInt(1, pId);
                preparedStatement.setInt(2, uId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    boolean result = resultSet.next();
                    System.out.println("[DEBUG] -> Resultado: " + result);
                    return result;
                }
            }
        } catch (Exception e) {
            System.err.println("[DEBUG] Erro em isOrganizerPalestra: " + e.getMessage());
            return false;
        }
    }

    public static boolean isAdmin(String idUser) {
        System.out.println("[DEBUG] AuthService.isAdmin(User=" + idUser + ")");
        try (Connection conexao = new Conexao().getConexao("app_admin")) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT 1 FROM usuario WHERE id = ? AND cargo = 'ADMIN'::user_role")) {
                
                int uId = (idUser == null || idUser.isEmpty()) ? 0 : Integer.parseInt(idUser);
                preparedStatement.setInt(1, uId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    boolean result = resultSet.next();
                    System.out.println("[DEBUG] -> Resultado: " + result);
                    return result;
                }
            }
        } catch (Exception e) {
            System.err.println("[DEBUG] Erro em isAdmin: " + e.getMessage());
            return false;
        }
    }

    public static boolean isViewer(String idUser) {
        System.out.println("[DEBUG] AuthService.isViewer(User=" + idUser + ")");
        try (Connection conexao = new Conexao().getConexao("app_admin")) {
            try (PreparedStatement preparedStatement = conexao.prepareStatement("SELECT 1 FROM usuario WHERE id = ? AND cargo = 'VIEWER'::user_role")) {
                
                int uId = (idUser == null || idUser.isEmpty()) ? 0 : Integer.parseInt(idUser);
                preparedStatement.setInt(1, uId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    boolean result = resultSet.next();
                    System.out.println("[DEBUG] -> Resultado: " + result);
                    return result;
                }
            }
        } catch (Exception e) {
            System.err.println("[DEBUG] Erro em isViewer: " + e.getMessage());
            return false;
        }
    }

    public static boolean isOrganizadorDoParticipante(String idOrganizador, String idParticipanteAlvo) {
        System.out.println("[DEBUG] AuthService.isOrganizadorDoParticipante(Org=" + idOrganizador + ", Participante=" + idParticipanteAlvo + ")");
        String sql = 
            """
                SELECT 1 
                FROM inscricao i 
                JOIN evento e ON (e.id = i.evento_id OR e.id = i.evento_privado_id) 
                WHERE i.usuario_id = ?  -- Corrigido para usuario_id
                AND e.organizador = ?;             
            """;

        try (
            Connection conexao = new Conexao().getConexao("app_admin");
            PreparedStatement preparedStatement = conexao.prepareStatement(sql)
        ) {
            int partId = (idParticipanteAlvo == null || idParticipanteAlvo.isEmpty()) ? 0 : Integer.parseInt(idParticipanteAlvo);
            int orgId = (idOrganizador == null || idOrganizador.isEmpty()) ? 0 : Integer.parseInt(idOrganizador);

            preparedStatement.setInt(1, partId);
            preparedStatement.setInt(2, orgId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                boolean result = resultSet.next();
                System.out.println("[DEBUG] -> Resultado: " + result);
                return result; 
            }

        } catch (Exception e) {
            System.err.println("[DEBUG] Erro em isOrganizadorDoParticipante: " + e.getMessage());
            return false;
        }
    }

    public static boolean isParticipantePalestra(String usuarioId, String palestraId) {
        System.out.println("[DEBUG] AuthService.isParticipantePalestra(User=" + usuarioId + ", Palestra=" + palestraId + ")");
        try (
            Connection conn = new Conexao().getConexao("app_admin");
            PreparedStatement stmt = conn.prepareStatement(
                """
                    SELECT 1
                    FROM inscricao i
                    JOIN palestra p ON (p.evento_id = i.evento_id OR p.evento_privado_id = i.evento_privado_id)
                    WHERE i.usuario_id = ? -- Corrigido para usuario_id
                    AND p.id = ?
                """
            )
        ) {
            int uId = (usuarioId == null || usuarioId.isEmpty()) ? 0 : Integer.parseInt(usuarioId);
            int pId = (palestraId == null || palestraId.isEmpty()) ? 0 : Integer.parseInt(palestraId);

            stmt.setInt(1, uId);
            stmt.setInt(2, pId);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean result = rs.next();
                System.out.println("[DEBUG] -> Resultado: " + result);
                return result;
            }

        } catch (Exception e) {
            System.err.println("[DEBUG] Erro em isParticipantePalestra: " + e.getMessage());
            return false;
        }
    }

    public static boolean isOrganizadorDoPalestrante(String user, String palestrante) {
        System.out.println("[DEBUG] AuthService.isOrganizadorDoPalestrante(Org=" + user + ", Palestrante=" + palestrante + ")");
        try (
            Connection conn = new Conexao().getConexao("app_admin");
            PreparedStatement stmt = conn.prepareStatement(
                """
                    SELECT 1 
                    FROM palestra_palestrante pp 
                    JOIN palestra p ON pp.palestra_id = p.id 
                    JOIN evento e ON (e.id = p.evento_id OR e.id = p.evento_privado_id) 
                    WHERE pp.perfil_palestrante_id = ? 
                    AND e.organizador = ?
                """
            )
        ) {
            int palestranteId = (palestrante == null || palestrante.isEmpty()) ? 0 : Integer.parseInt(palestrante);
            int orgId = (user == null || user.isEmpty()) ? 0 : Integer.parseInt(user);

            stmt.setInt(1, palestranteId);
            stmt.setInt(2, orgId);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean result = rs.next();
                System.out.println("[DEBUG] -> Resultado: " + result);
                return result;
            }

        } catch (Exception e) {
            System.err.println("[DEBUG] Erro em isOrganizadorDoPalestrante: " + e.getMessage());
            return false;
        }
    }
}