package evento;

import java.time.LocalDate;

public class Evento {
    
    private int id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String localidade;
    private int organizadorId;
    private boolean privado;
    private String detalhes;

    public Evento() {}

    public Evento(int id, String nome, LocalDate dataInicio, LocalDate dataFim, String localidade, int organizadorId, boolean privado) {
        this.id = id;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.localidade = localidade;
        this.organizadorId = organizadorId;
        this.privado = privado;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public String getLocalidade() { return localidade; }
    public int getOrganizadorId() { return organizadorId; }
    public boolean isPrivado() { return privado; }
    public String getDetalhes() { return detalhes; }
    
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }
    public void setOrganizadorId(int organizadorId) { this.organizadorId = organizadorId; }
    public void setPrivado(boolean privado) { this.privado = privado; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }
}