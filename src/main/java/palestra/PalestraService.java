package palestra;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evento.EventoService;
import exceptions.ChaveNaoGeradaException;
import palestra.dto.PalestraCriacaoDTO;
import palestra.dto.PalestraDashboardDTO;
import palestra.dto.PalestraOpcaoDTO;
import palestra.dto.PalestraPerfilDTO;
import palestrante.PalestranteService;
import persistencias.AService;
import persistencias.Conexao;

public class PalestraService extends AService<
    PalestraDashboardDTO, 
    PalestraPerfilDTO, 
    PalestraCriacaoDTO, 
    PalestraOpcaoDTO, 
    Palestra, 
    PalestraDAO, 
    PalestraFactory
> {
    private PalestranteService palestranteService;
    private EventoService eventoService;

    public PalestraService(PalestraDAO dao, PalestraFactory factory, PalestranteService palestranteService,
            EventoService eventoService) {
        super(dao, factory);
        this.palestranteService = palestranteService;
        this.eventoService = eventoService;
    }

    @Override public Map<String, String> validador(PalestraCriacaoDTO criacaoDTO) {
        Map<String, String> erros = new HashMap<>();

        String nome = criacaoDTO.getNome();
        if (nome == null || nome.isBlank()) erros.put("nome", "O nome da palestra é obrigatório.");
        if (nome != null && nome.length() > 200) erros.put("nome", "O nome excede o limite de 200 caracteres.");

        String duracaoStr = criacaoDTO.getDuracao();
        if (duracaoStr == null || duracaoStr.isBlank()) erros.put("duracao", "A duração é obrigatória.");
        else try {
            if (Integer.parseInt(duracaoStr) <= 0) erros.put("duracao", "A duração deve ser um número positivo.");
        } catch (NumberFormatException e) {
            erros.put("duracao", "A duração deve ser um número válido.");
        }

        String dataHoraStr = criacaoDTO.getDataHoraInicio();
        if (dataHoraStr != null && !dataHoraStr.isBlank()) try {
            LocalDateTime.parse(dataHoraStr);
        } catch (DateTimeParseException e) {
            erros.put("dataHoraInicio", "Formato inválido para Data e Hora. Use YYYY-MM-DDTHH:MM.");
        }

        try (Connection conexao = new Conexao().getConexao()) {
            String eventoIdStr = criacaoDTO.getEventoId();
            if (eventoIdStr == null || eventoIdStr.isBlank()) {
                erros.put("eventoId", "O evento associado é obrigatório.");
            } else try {
                int idParse = Integer.parseInt(eventoIdStr);
                if (idParse <= 0) throw new NumberFormatException();
                else if (!this.eventoService.existe(idParse, conexao)) erros.put("eventoId","O evento selecionado não existe");
            } catch (NumberFormatException e) {
                erros.put("eventoId", "O ID do evento selecionado é inválido.");
            }

            List<String> palestranteIds = criacaoDTO.getPalestranteId();
            if (palestranteIds == null || palestranteIds.isEmpty()) erros.put("eventoId", "Pelo menos um palestrante deve ser selecionado.");
            else for (String idStr : palestranteIds) try {
                int idParse = Integer.parseInt(idStr);
                if (idParse <= 0) throw new NumberFormatException();
                else if (!this.palestranteService.existe(Integer.parseInt(idStr), conexao)) erros.put("palestranteId", "Algum(ns) palestrante(s) selecionado(s) nao existe(m)");
            } catch (NumberFormatException e) {
                erros.put("palestranteIds", "A lista de palestrantes contém IDs numéricos inválidos.");
                break;
            }
        } catch(SQLException e) {
            throw new RuntimeException("Erro de banco de dados durante a validação: " + e.getMessage(), e);
        }

        return erros;
    }

    @Override public void salvar(PalestraCriacaoDTO criacaoDTO) throws SQLException {
        Palestra palestra = this.factory.toEntidade(criacaoDTO);
        try (Connection conexao = new Conexao().getConexao()) {
            conexao.setAutoCommit(false);
            try {
                int idPalestra = this.dao.adicionar(conexao, palestra);
                for (String id : criacaoDTO.getPalestranteId()) this.dao.addPalestrante(conexao,idPalestra, Integer.parseInt(id));
                conexao.commit();
            } catch (SQLException | ChaveNaoGeradaException | NumberFormatException e) {
                try {
                    conexao.rollback();
                } catch (SQLException rollbackEx) {
                    e.addSuppressed(rollbackEx);
                }

                throw new RuntimeException("Erro ao salvar palestra: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro de banco de dados (conexão ou transação): " + e.getMessage(), e);
        }
    }

    @Override public PalestraPerfilDTO getPerfil(String chave, String coluna) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPerfil'");
    }

    @Override public Map<String, Object> elementosFormulario() throws SQLException {
        Map<String, Object> model = super.elementosFormulario();

        try (Connection conexao = new Conexao().getConexao()) {
            model.put("palestrante", this.palestranteService.getOpcoes(conexao));
            model.put("evento", this.eventoService.getOpcoes(conexao));
        }

        return model;
    }

}