package evento.dto;

public class EventoResumo {
    private int id;
    private String nome;
    private String localidade;
    private String data;

    public EventoResumo() { }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getLocalidade() { return localidade; }
    public String getData() { return data; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }
    public void setData(String data) { this.data = data; }
}