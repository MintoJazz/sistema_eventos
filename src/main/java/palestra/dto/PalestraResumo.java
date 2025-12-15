package palestra.dto;

import persistencias.OpcaoDTO;

public class PalestraResumo {
    private int id;
    private String nome;
    private String dataHora;
    private OpcaoDTO evento;

    public PalestraResumo() {}

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDataHora() { return dataHora; }
    public OpcaoDTO getEvento() { return evento; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }
    public void setEvento(OpcaoDTO evento) { this.evento = evento; }
}