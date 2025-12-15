package palestra.dto;

public class CronogramaItem {
    private int id;
    private String nome;
    private String horaInicio;
    private int duracao;

    public CronogramaItem() {}

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getHoraInicio() { return horaInicio; }
    public int getDuracao() { return duracao; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    public void setDuracao(int duracao) { this.duracao = duracao; }
}