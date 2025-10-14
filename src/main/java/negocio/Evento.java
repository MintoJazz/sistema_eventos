package negocio;

import java.sql.Date;
import persistencias.Formatadores;

public class Evento {
    private int id;
    private String nome, localidade, status;
    private Date dataInicio; // Campo de String removido
    private Date dataFim;    // Campo de String removido

    public Evento(int id, String nome, Date dataInicio, Date dataFim, String localidade, String status) {
        this.id = id;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.localidade = localidade;
        this.status = status;
    }

    public Evento(int id, String nome, Date dataInicio, Date dataFim, String localidade) {
        this(id, nome, dataInicio, dataFim, localidade, null); // Encaminha para o construtor principal
    }

    // Getters para os dados brutos
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getLocalidade() { return localidade; }
    public Date getDataInicio() { return dataInicio; }
    public Date getDataFim() { return dataFim; }
    public String getStatus() { return status; }
    
    // Getters formatados para usar no Mustache
    public String getDataInicioFormatada() {
        return Formatadores.data2String(this.dataInicio);
    }
    public String getDataFimFormatada() {
        return Formatadores.data2String(this.dataFim);
    }

    // Setters (sem mudanças)
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(Date dataFim) { this.dataFim = dataFim; }
    public void setStatus(String status) { this.status = status; }
}