package evento.privado;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import evento.EventoService;
import evento.dto.EventoPrivadoForm;
import evento.dto.EventoPrivadoPerfil;
import exceptions.PermissaoNegadaException;
import palestra.PalestraService;
import persistencias.Conexao;
import persistencias.ServiceCRUD;
import usuario.UsuarioService;

public class EventoPrivadoService extends ServiceCRUD<EventoPrivado, EventoPrivadoPerfil, EventoPrivadoForm, EventoPrivadoDAO, EventoPrivadoFactory> {

    private EventoService eventoService;
    private PalestraService palestraService;
    private UsuarioService usuarioService;

    public EventoPrivadoService(EventoPrivadoDAO dao, EventoPrivadoFactory factory) {
        super(dao, factory);
    }
    
    public void setDependencias(EventoService eventoService, PalestraService palestraService, UsuarioService usuarioService) {
        this.eventoService = eventoService;
        this.palestraService = palestraService;
        this.usuarioService = usuarioService;
    }

    @Override public Map<String, String> validador(EventoPrivadoForm form) {
        Map<String, String> erros = this.eventoService.validador(form);

        if (form.getSenhaAcesso() == null || form.getSenhaAcesso().isBlank()) erros.put("senhaAcesso", "A senha de acesso é obrigatória para eventos privados.");
        else if (form.getSenhaAcesso().length() < 3) erros.put("senhaAcesso", "A senha deve ter pelo menos 3 caracteres.");

        return erros;
    }

    @Override public EventoPrivadoPerfil getPerfil(String role, int id) {
        try (Connection conexao = new Conexao().getConexao(role)) {
            EventoPrivadoPerfil perfil = this.getOne(conexao, id, EventoPrivadoPerfil.class);

            perfil.setCronograma(this.palestraService.getCronograma(conexao, perfil.getId()));
            perfil.setOrganizador(this.usuarioService.getOrganizador(conexao, perfil.getId()));

            return perfil;

        } catch (SQLException e) {
            if ("42501".equals(e.getSQLState())) throw new PermissaoNegadaException("Permissão Negada: leitura para o role " + role, e);
            throw new RuntimeException("Falha na conexão: " + e.getMessage(), e);
        }
    }

    @Override public Map<String, Object> elementosFormulario(String role) {
        return this.eventoService.elementosFormulario(role);
    }
}