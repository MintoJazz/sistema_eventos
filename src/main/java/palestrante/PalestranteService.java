package palestrante;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.PermissaoNegadaException;
import palestra.PalestraService;
import palestrante.dto.PalestranteForm;
import palestrante.dto.PalestrantePerfil;
import persistencias.Conexao;
import persistencias.OpcaoDTO;
import persistencias.ServiceCRUD;
import usuario.UsuarioService;

public class PalestranteService extends ServiceCRUD<Palestrante,PalestrantePerfil,PalestranteForm,PalestranteDAO,PalestranteFactory> {
    private PalestraService palestraService;
    private UsuarioService usuarioService;

    public PalestranteService(PalestranteDAO dao, PalestranteFactory factory) {
        super(dao, factory);
    }

    public void setPalestraService(PalestraService palestraService) { this.palestraService = palestraService; }
    public void setUsuarioService(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @Override public Map<String, Object> elementosFormulario(String role) {
        Map<String, Object> map = new java.util.HashMap<>();
        
        List<OpcaoDTO> todosUsuarios = this.usuarioService.getAll(role, OpcaoDTO.class);
        List<OpcaoDTO> jaPalestrantes = this.getAll(role, OpcaoDTO.class);
        
        List<Integer> idsPalestrantes = jaPalestrantes.stream().map(OpcaoDTO::getId).toList();
        List<OpcaoDTO> candidatos = todosUsuarios.stream().filter(u -> !idsPalestrantes.contains(u.getId())).toList();

        map.put("usuarios", candidatos);
        
        return map;
    }

    @Override public Map<String, String> validador(PalestranteForm form) {
        Map<String, String> erros = new HashMap<>();
        if (form.getUsuarioId() <= 0) erros.put("usuarioId", "Selecione um usuário válido.");
        if (form.getBiografia() == null || form.getBiografia().isBlank()) erros.put("biografia", "Biografia é obrigatória.");
        return erros;
    }

    @Override public PalestrantePerfil getPerfil(String role, int id) {
        try (Connection conexao = new Conexao().getConexao(role)) {
            PalestrantePerfil perfil = this.getOne(conexao, id, PalestrantePerfil.class);
            perfil.setPalestras(this.palestraService.getAgenda(conexao, perfil.getId()));
            return perfil;
        } catch (SQLException e) {
            if (e.getSQLState().equals("42501")) throw new PermissaoNegadaException("Permissão Negada: leitura para o role " + role,e);
            throw new RuntimeException("Falha na conexão: " + e.getMessage(), e);
        }
    }

    public List<OpcaoDTO> getPalestrantes(Connection conexao, int id) throws SQLException {
        List<OpcaoDTO> lista = new ArrayList<>();

        List<Palestrante> palestrantes = this.dao.selectPalestrantes(conexao,id);

        System.out.println("[DEBUG] Palestrantes encontrados no banco para ID " + id + ": " + palestrantes.size()); // <--- ADICIONE ISSO

        for (Palestrante palestrante : palestrantes) lista.add(this.factory.toDTO(palestrante,OpcaoDTO.class));
        
        return lista;
    }
}