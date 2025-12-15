package evento.dto;

public class EventoDashboard {
    
    private int id;
    private String nome;
    private String dataInicio;
    private String dataFim;
    private String localidade;
    private String linkVer;
    private String linkEditar;
    private String linkDeletar;

    public EventoDashboard() {}

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDataInicio() { return dataInicio; }
    public String getDataFim() { return dataFim; }
    public String getLocalidade() { return localidade; }
    public String getLinkVer() { return linkVer; }
    public String getLinkEditar() { return linkEditar; }
    public String getLinkDeletar() { return linkDeletar; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }
    public void setLinkVer(String linkVer) { this.linkVer = linkVer; }
    public void setLinkEditar(String linkEditar) { this.linkEditar = linkEditar; }
    public void setLinkDeletar(String linkDeletar) { this.linkDeletar = linkDeletar; }
}