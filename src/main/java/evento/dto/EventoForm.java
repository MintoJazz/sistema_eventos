package evento.dto;

public class EventoForm {
    
    private int id;
    private String nome;
    private String dataInicio;
    private String dataFim;
    private String localidade;
    private int organizadorId;
    private boolean privado;
    private String senhaAcesso;
    private String convidados;

    public EventoForm() {}

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDataInicio() { return dataInicio; }
    public String getDataFim() { return dataFim; }
    public String getLocalidade() { return localidade; }
    public int getOrganizadorId() { return organizadorId; }
    public boolean isPrivado() { return privado; }
    public String getSenhaAcesso() { return senhaAcesso; }
    public String getConvidados() { return convidados; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }
    public void setOrganizadorId(int organizadorId) { this.organizadorId = organizadorId; }
    public void setPrivado(boolean privado) { this.privado = privado; }
    public void setSenhaAcesso(String senhaAcesso) { this.senhaAcesso = senhaAcesso; }
    public void setConvidados(String convidados) { this.convidados = convidados; }
}