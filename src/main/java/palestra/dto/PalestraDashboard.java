package palestra.dto;

public class PalestraDashboard {
    
    private int id;
    private String nome;
    private int duracao; 
    private String dataHora;
    private String nomeEvento;

    public PalestraDashboard() {};

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getDuracao() { return duracao; }
    public String getDataHora() { return dataHora; }
    public String getNomeEvento() { return nomeEvento; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDuracao(int duracao) { this.duracao = duracao; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }
    public void setNomeEvento(String nomeEvento) { this.nomeEvento = nomeEvento; }
}