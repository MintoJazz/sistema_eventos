package persistencias;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.PermissaoNegadaException;
import exceptions.RecursoNaoEncontradoException;
import exceptions.ValidacaoException;

public abstract class ServiceCRUD<Entidade, Perfil, Form, DAO extends DAOCRUD<Entidade>, Factory extends AFactory<Entidade>> {
    protected final DAO dao;
    protected final Factory factory;
    
    public ServiceCRUD(DAO dao, Factory factory) {
        this.dao = dao;
        this.factory = factory;
    }
    
    public abstract Map<String, String> validador(Form criacaoDTO);

    public abstract Perfil getPerfil(String role, int id);

    public <DTO> DTO getOne(String role, int id, Class<DTO> classDTO){
        try (Connection conexao = new Conexao().getConexao(role)) {
            return this.getOne(conexao, id, classDTO);
        } catch (SQLException e) {
            if ("42501".equals(e.getSQLState())) throw new PermissaoNegadaException("Permissão Negada: leitura para o role " + role, e);
            throw new RuntimeException("Falha na conexão: " + e.getMessage(), e);
        }
    }
    
    public <DTO> DTO getOne(Connection conexao, int id, Class<DTO> classDTO) throws SQLException {
        Entidade entidade = this.dao.selectOne(conexao, id);
        if (entidade == null) throw new RecursoNaoEncontradoException("Recurso não encontrado com ID: " + id);
        return this.factory.toDTO(entidade, classDTO);
    }
    
    public Map<String, Object> elementosFormulario(String role) {
        return new HashMap<>();
    }

    // --- CREATE ---
    public int create(Connection conexao, String role, Form dto) throws SQLException {
        return this.dao.insert(conexao, this.factory.toEntidade(dto));
    }
    
    public int create(String role, Form dto) { 
        try (Connection conexao = new Conexao().getConexao(role)) {
            Map<String, String> erros = this.validador(dto);
            if (!erros.isEmpty()) throw new ValidacaoException(erros);
            return this.create(conexao, role, dto);
        } catch (SQLException e) {
            if ("42501".equals(e.getSQLState())) throw new PermissaoNegadaException("Permissão Negada: criação para o role " + role, e);
            throw new RuntimeException("Falha na conexão: " + e.getMessage(), e);
        }
    }

    // --- READ ALL ---
    public <DTO> List<DTO> getAll(String role, Class<DTO> classDTO) {
        try (Connection conexao = new Conexao().getConexao(role)) {
            return this.getAll(conexao, classDTO);
        } catch (SQLException e) {
            throw new RuntimeException("Falha na conexão: " + e.getMessage(), e);
        }
    }

    public <DTO> List<DTO> getAll(Connection conexao, Class<DTO> classDTO) throws SQLException {
        List<DTO> dto = new ArrayList<>();
        for (Entidade entidade : this.dao.selectAll(conexao)) dto.add(this.factory.toDTO(entidade, classDTO));
        System.out.println("CHEGOU NAS OPÇÕES");
        return dto;
    }

    // --- UPDATE ---
    public int update(Connection conexao, Form dto) throws SQLException {
        return this.dao.update(conexao, this.factory.toEntidade(dto));
    }
    
    public void update(String role, Form dto) {
        try (Connection conexao = new Conexao().getConexao(role)) {
            Map<String, String> erros = this.validador(dto);
            if (!erros.isEmpty()) throw new ValidacaoException(erros);
            this.update(conexao, dto);
        }  catch (SQLException e) {
            if ("42501".equals(e.getSQLState())) throw new PermissaoNegadaException("Permissão Negada: update para o role " + role, e);
            throw new RuntimeException("Falha na conexão: " + e.getMessage(), e);
        }
    }

    // --- DELETE ---
    public void delete(Connection conexao, int id) throws SQLException {
        this.dao.delete(conexao, id);
    }

    public void delete(String role, String idStr) {
        try (Connection conexao = new Conexao().getConexao(role)) {
            int id = Integer.parseInt(idStr); // Parse seguro
            this.delete(conexao, id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido para exclusão.");
        } catch (SQLException e) {
            if ("42501".equals(e.getSQLState())) throw new PermissaoNegadaException("Permissão Negada: delete para o role: " +  role, e);
            throw new RuntimeException("Falha na conexão: " + e.getMessage(), e);
        }
    }
}