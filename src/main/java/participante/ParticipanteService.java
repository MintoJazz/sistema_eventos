package participante;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import evento.EventoService;
import participante.dto.ParticipanteCriacaoDTO;
import participante.dto.ParticipanteDashboardDTO;
import participante.dto.ParticipanteOpcaoDTO;
import participante.dto.ParticipantePerfilDTO;
import persistencias.AService;
import persistencias.Conexao;

public class ParticipanteService extends AService<
	ParticipanteDashboardDTO,
	ParticipantePerfilDTO,
	ParticipanteCriacaoDTO,
	ParticipanteOpcaoDTO,
	Participante, 
	ParticipanteDAO, 
	ParticipanteFactory
> {

    private EventoService eventoService;

    public ParticipanteService(ParticipanteDAO dao, ParticipanteFactory factory, EventoService eventoService) {
        super(dao, factory);
        this.eventoService = eventoService;
        //TODO Auto-generated constructor stub
    }

    @Override public Map<String, String> validador(ParticipanteCriacaoDTO criacaoDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validador'");
    }

    @Override public void salvar(ParticipanteCriacaoDTO criacaoDTO) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'salvar'");
    }

    @Override public ParticipantePerfilDTO getPerfil(String valor, String coluna) throws SQLException {
        ParticipantePerfilDTO perfil = null;

        try (Connection conexao = new Conexao().getConexao()) {
            perfil = factory.getPerfil(this.dao.getOne(conexao, coluna, valor));
            perfil.setEventos(this.eventoService.listar(perfil.getId(), conexao));
        }

        return perfil;
    }
    
}
