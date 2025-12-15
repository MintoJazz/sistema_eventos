package palestra;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import evento.EventoService;
import palestra.dto.CronogramaItem;
import palestra.dto.PalestraForm;
import palestra.dto.PalestraPerfil;
import palestra.dto.PalestraResumo;
import palestrante.PalestranteService;
import persistencias.Conexao;
import persistencias.OpcaoDTO;
import persistencias.ServiceCRUD;
import usuario.UsuarioService;
import util.Formatadores;

public class PalestraService extends ServiceCRUD<Palestra, PalestraPerfil, PalestraForm, PalestraDAO, PalestraFactory> {

    private UsuarioService usuarioService;
    private PalestranteService palestranteService;
    private EventoService eventoService;

    public PalestraService(PalestraDAO dao, PalestraFactory factory) { super(dao, factory); }

    public void setPalestranteService(PalestranteService palestranteService) { this.palestranteService = palestranteService; }
    public void setEventoService(EventoService eventoService) { this.eventoService = eventoService; }
    public void setUsuarioService(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @Override public <DTO> DTO getOne(Connection conexao, int id, Class<DTO> classDTO) throws SQLException {
        Palestra entidade = this.dao.selectOne(conexao, id);
        if (entidade == null) throw new exceptions.RecursoNaoEncontradoException("Palestra não encontrada com ID: " + id);

        if (classDTO.equals(PalestraForm.class)) {
            List<Integer> ids = this.dao.selectIdsPalestrantes(conexao, id);
            
            entidade.setPalestrantesIds(ids);
        }

        return this.factory.toDTO(entidade, classDTO);
    }

    @Override public Map<String, Object> elementosFormulario(String role) {
        Map<String, Object> map = new java.util.HashMap<>();
        
        map.put("eventos", this.eventoService.getAll(role, OpcaoDTO.class));
        map.put("palestrantes", this.usuarioService.getAll(role, OpcaoDTO.class));
        
        return map;
    }

    @Override public Map<String, String> validador(PalestraForm form) {
        Map<String, String> erros = new java.util.HashMap<>();

        if (form.getNome() == null || form.getNome().isBlank()) erros.put("nome", "O nome da palestra é obrigatório.");
        else if (form.getNome().length() > 200) erros.put("nome", "O nome deve ter no máximo 200 caracteres.");

        if (form.getDuracao() <= 0) erros.put("duracao", "A duração deve ser de no mínimo 1 minuto.");
        if (form.getEventoId() <= 0) erros.put("eventoId", "É obrigatório selecionar um evento para a palestra.");

        if (form.getDataHoraInicio() == null || form.getDataHoraInicio().isBlank()) erros.put("dataHoraInicio", "A data e hora de início são obrigatórias.");

        return erros;
    }

    @Override public PalestraPerfil getPerfil(String role, int id) {
        try (Connection conexao = new Conexao().getConexao(role)) {
            PalestraPerfil perfil = this.getOne(conexao, id, PalestraPerfil.class);
            perfil.setPalestrantes(this.palestranteService.getPalestrantes(conexao, perfil.getId()));
            perfil.setEvento(this.eventoService.getEvento(conexao, perfil.getId()));
            return perfil;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CronogramaItem> getCronograma(Connection conexao, int id) throws SQLException {
        List<CronogramaItem> lista = new ArrayList<>();
        for (Palestra palestra : this.dao.selectCronograma(conexao,id)) lista.add(this.factory.toDTO(palestra, CronogramaItem.class));
        
        return lista;
    }

    public List<PalestraResumo> getAgenda(Connection conexao, int id) throws SQLException {
        List<PalestraResumo> lista = new ArrayList<>();
        Map<Integer, OpcaoDTO> mapa = Formatadores.mapear(this.eventoService.getAll(conexao, OpcaoDTO.class), OpcaoDTO::getId);

        for (Palestra palestra : this.dao.selectAgenda(conexao,id)) {
            PalestraResumo resumo = this.factory.toDTO(palestra, PalestraResumo.class);
            resumo.setEvento(mapa.get(palestra.getEventoId())); 

            lista.add(resumo);
        }

        return lista;
    }
}