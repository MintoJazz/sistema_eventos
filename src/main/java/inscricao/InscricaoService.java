package inscricao;

import java.util.Map;
import inscricao.dto.InscricaoForm;
import persistencias.ServiceCRUD;

public class InscricaoService extends ServiceCRUD<Inscricao, Inscricao, InscricaoForm, InscricaoDAO, InscricaoFactory> {

    public InscricaoService(InscricaoDAO dao, InscricaoFactory factory) {
        super(dao, factory);
    }

    @Override public Map<String, String> validador(InscricaoForm form) {
        Map<String, String> erros = new java.util.HashMap<>();
        
        if (form.getEventoId() <= 0) erros.put("eventoId", "Evento inválido.");
        if (form.getUsuarioId() <= 0) erros.put("usuarioId", "Usuário não identificado. Faça login novamente.");

        return erros;
    }

    @Override public Inscricao getPerfil(String role, int id) { return this.getOne(role, id, Inscricao.class); }
}