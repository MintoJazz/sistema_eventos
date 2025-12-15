package usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import evento.EventoService;
import exceptions.PermissaoNegadaException;
import exceptions.RecursoNaoEncontradoException;
import palestra.PalestraService;
import persistencias.Conexao;
import persistencias.OpcaoDTO;
import persistencias.ServiceCRUD;
import usuario.dto.UsuarioForm;
import usuario.dto.UsuarioPerfil;

public class UsuarioService extends ServiceCRUD<Usuario, UsuarioPerfil, UsuarioForm, UsuarioDAO, UsuarioFactory> {
    PalestraService palestraService;
    EventoService eventoService;
    
    public UsuarioService(UsuarioDAO dao, UsuarioFactory factory) { super(dao, factory); }

    public PalestraService getPalestraService() { return palestraService; }
    public EventoService getEventoService() { return eventoService; }
    
    public void setPalestraService(PalestraService palestranteService) { this.palestraService = palestranteService; }
    public void setEventoService(EventoService eventoService) { this.eventoService = eventoService; }
    
    @Override public Map<String, String> validador(UsuarioForm form) {
        Map<String, String> erros = new HashMap<>();

        if (form.getNome() == null || form.getNome().isBlank()) erros.put("nome", "O nome é obrigatório.");
        else if (form.getNome().length() > 200) erros.put("nome", "O nome deve ter no máximo 200 caracteres.");

        if (form.getCpf() == null || form.getCpf().isBlank()) erros.put("cpf", "O CPF é obrigatório.");
        else if (form.getCpf().replaceAll("\\D", "").length() != 11) erros.put("cpf", "O CPF deve conter 11 dígitos.");

        if (form.getEmail() == null || form.getEmail().isBlank()) erros.put("email", "O email é obrigatório.");
        else if (!form.getEmail().contains("@")) erros.put("email", "Formato de email inválido.");

        if (form.getSenha() == null || form.getSenha().isBlank()) erros.put("senha", "A senha é obrigatória.");
        else if (form.getSenha().length() < 6) erros.put("senha", "A senha deve ter no mínimo 6 caracteres.");

        return erros;
    }

    @Override public UsuarioPerfil getPerfil(String role, int id) {
        try (Connection conexao = new Conexao().getConexao(role)) {
            Usuario entidade = dao.selectOne(conexao, id);
            if (entidade == null) throw new RecursoNaoEncontradoException("Usuário não encontrado para o id: " + id);

            System.out.println("CHEGOOUU AQUIII ID: " + entidade.getId());

            UsuarioPerfil perfil = this.factory.toDTO(entidade, UsuarioPerfil.class);
            perfil.setEventos(this.eventoService.getInscricoes(conexao, id));
            perfil.setPalestras(this.palestraService.getAgenda(conexao, id));
            perfil.setEventosOrganizados(this.eventoService.getEventosOrganizados(conexao,id));

            return perfil;
        } catch (SQLException e) {
            if ("42501".equals(e.getSQLState())) throw new PermissaoNegadaException("Permissão Negada: leitura para o role " + role, e);
            throw new RuntimeException("Falha na conexão: " + e.getMessage(), e);
        }
    }

    public OpcaoDTO getOrganizador(Connection conexao, int id) throws SQLException{
        return this.factory.toDTO(this.dao.selectOrganizador(conexao, id), OpcaoDTO.class);
    }

}