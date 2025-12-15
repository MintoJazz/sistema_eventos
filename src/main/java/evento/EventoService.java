package evento;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import evento.dto.EventoForm;
import evento.dto.EventoPerfil;
import evento.dto.EventoResumo;
import exceptions.PermissaoNegadaException;
import palestra.PalestraService;
import persistencias.Conexao;
import persistencias.OpcaoDTO;
import persistencias.ServiceCRUD;
import usuario.UsuarioService;

public class EventoService extends ServiceCRUD<Evento, EventoPerfil, EventoForm, EventoDAO, EventoFactory>{

    private PalestraService palestraService;
    private UsuarioService usuarioService;

    public EventoService(EventoDAO dao, EventoFactory factory) { super(dao, factory); }

    public void setPalestraService(PalestraService palestraService) { this.palestraService = palestraService; }
    public void setUsuarioService(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @Override  public Map<String, Object> elementosFormulario(String role) {
        Map<String, Object> map = new java.util.HashMap<>();
        
        List<OpcaoDTO> listaOrganizadores = this.usuarioService.getAll(role, OpcaoDTO.class);
        map.put("organizadores", listaOrganizadores);
        
        return map;
    }

    @Override public Map<String, String> validador(EventoForm form) {
        Map<String, String> erros = new java.util.HashMap<>();
        java.time.LocalDate dataInicio = null;
        java.time.LocalDate dataFim = null;

        if (form.getNome() == null || form.getNome().isBlank()) erros.put("nome", "O nome do evento é obrigatório.");
        else if (form.getNome().length() > 200) erros.put("nome", "O nome deve ter no máximo 200 caracteres.");

        if (form.getLocalidade() == null || form.getLocalidade().isBlank()) erros.put("localidade", "A localidade é obrigatória.");
        if (form.getOrganizadorId() <= 0) erros.put("organizadorId", "É necessário selecionar um organizador.");


        if (form.getDataInicio() == null || form.getDataInicio().isBlank()) erros.put("dataInicio", "A data de início é obrigatória.");
        else try {
            dataInicio = java.time.LocalDate.parse(form.getDataInicio());
        } catch (java.time.format.DateTimeParseException e) {
            erros.put("dataInicio", "Data de início inválida.");
        }

        if (form.getDataFim() == null || form.getDataFim().isBlank()) erros.put("dataFim", "A data de fim é obrigatória.");
        else try {
            dataFim = java.time.LocalDate.parse(form.getDataFim());
        } catch (java.time.format.DateTimeParseException e) {
            erros.put("dataFim", "Data de fim inválida.");
        }

        if (dataInicio != null && dataFim != null && dataFim.isBefore(dataInicio)) erros.put("dataFim", "A data de fim não pode ser anterior à data de início.");

        return erros;
    }

    @Override public EventoPerfil getPerfil(String role, int id) {
        try (Connection conexao = new Conexao().getConexao(role)) {
            EventoPerfil perfil = this.getOne(conexao, id, EventoPerfil.class);

            perfil.setCronograma(this.palestraService.getCronograma(conexao, perfil.getId()));
            perfil.setOrganizador(this.usuarioService.getOrganizador(conexao, perfil.getId()));

            return perfil;
        } catch (SQLException e) {
            if (e.getSQLState().equals("42501")) throw new PermissaoNegadaException("Permissão Negada: leitura para o role " + role,e);
            throw new RuntimeException("Falha na conexão: " + e.getMessage(), e);
        }
    }

    public List<EventoResumo> getInscricoes(Connection conexao, int id) throws SQLException {
        List<EventoResumo> lista = new ArrayList<>();
        for (Evento evento : this.dao.selectEventos(conexao,id)) lista.add(this.factory.toDTO(evento, EventoResumo.class));

        return lista;
    }

    public OpcaoDTO getEvento(Connection conexao, int id) throws SQLException {
        return this.factory.toDTO(this.dao.selectEvento(conexao,id), OpcaoDTO.class);
    }

    public List<EventoResumo> getEventosOrganizados(Connection conexao, int id) throws SQLException {
        List<EventoResumo> lista = new ArrayList<>();
        for (Evento evento : this.dao.selectEventosOrganizados(conexao,id)) lista.add(this.factory.toDTO(evento, EventoResumo.class));

        return lista;
    }

    public void setDetalhes(String role, int id, String json) {
        try (Connection conexao = new Conexao().getConexao(role)) {
            dao.updateDetalhes(conexao, id, json);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Evento getEntidade(String role, int id) {
        try (Connection conexao = new Conexao().getConexao(role)) {
            Evento evento = this.dao.selectOne(conexao, id);
            
            if (evento == null) {
                throw new exceptions.RecursoNaoEncontradoException("Evento não encontrado com ID: " + id);
            }
            return evento;
            
        } catch (SQLException e) {
            if ("42501".equals(e.getSQLState())) {
                throw new PermissaoNegadaException("Permissão Negada: leitura para o role " + role, e);
            }
            throw new RuntimeException("Falha na conexão: " + e.getMessage(), e);
        }
    }
    
}