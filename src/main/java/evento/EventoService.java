package evento;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evento.dto.EventoCriacaoDTO;
import evento.dto.EventoDashboardDTO;
import evento.dto.EventoOpcaoDTO;
import evento.dto.EventoPerfilDTO;
import persistencias.AService;
import persistencias.Conexao;

public class EventoService extends AService <
    EventoDashboardDTO, 
	EventoPerfilDTO, 
	EventoCriacaoDTO,
	EventoOpcaoDTO,
	Evento, 
	EventoDAO, 
	EventoFactory
> {

    public EventoService(EventoDAO dao, EventoFactory factory) {
        super(dao, factory);
    }

    @Override public Map<String, String> validador(EventoCriacaoDTO criacaoDTO) {
        Map<String, String> erros = new HashMap<>();

        String nome = criacaoDTO.getNome();
        if (nome == null || nome.isBlank()) erros.put("nome", "O nome do evento é obrigatório.");
        else if (nome.length() > 200) erros.put("nome", "O nome do evento não pode exceder 200 caracteres.");
        // TODO: Adicionar validação de nome duplicado (requer chamada ao DAO)
        // if (dao.buscarPorNome(nome) != null) erros.put("nome", "Já existe um evento com este nome.");

        String local = criacaoDTO.getLocal();
        if (local == null || local.isBlank()) erros.put("local", "A localidade do evento é obrigatória.");

        LocalDate dataInicioParsed = null;
        LocalDate dataFimParsed = null;

        String dataInicioStr = criacaoDTO.getDataInicio();
        if (dataInicioStr == null || dataInicioStr.isBlank()) erros.put("dataInicio", "A data de início é obrigatória.");
        else {
            try {
                dataInicioParsed = LocalDate.parse(dataInicioStr);
                // TODO: Verificar se precisa ocorrer validação da data de início - se ela precisa ser no futuro (regra de negócio comum)
                // if (dataInicioParsed.isBefore(LocalDate.now())) erros.put("dataInicio", "A data de início não pode ser no passado.");
            } catch (DateTimeParseException e) {
                erros.put("dataInicio", "Formato inválido para a data de início. Use YYYY-MM-DD.");
            }
        }

        if (dataInicioParsed != null && dataFimParsed != null && dataFimParsed.isBefore(dataInicioParsed)) erros.put("dataFim", "A data de fim não pode ser anterior à data de início.");

        // TODO: Validação dos JSONB (Detalhes & Metadados) - Como verificar se é um JSON válido??
        
        // Validação do Material: Limite de Tamanho)
        byte[] material = criacaoDTO.getMaterial();
        if (material != null && material.length > 5 * 1024 * 1024) erros.put("material", "O arquivo de material excede o limite de 5MB.");

        return erros;
    }

    @Override public void salvar(EventoCriacaoDTO criacaoDTO) throws SQLException {
        try (Connection conexao = new Conexao().getConexao()) {
            this.dao.adicionar(conexao, this.factory.toEntidade(criacaoDTO));
        }
    }

    @Override public EventoPerfilDTO getPerfil(String chave, String coluna) throws SQLException {
        try (Connection conexao = new Conexao().getConexao()) {
            Evento evento = this.dao.getOne(conexao, coluna, chave);

            if (evento != null) return this.factory.getPerfil(evento);
        } throw new SQLException("Evento Nao Encontrado");
    }

    public List<EventoDashboardDTO> listar(int id, Connection conexao) throws SQLException {
        return this.factory.getDashboard(this.dao.getAll(conexao, id));
    }
}