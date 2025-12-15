package palestra.dto;

import java.util.ArrayList;
import java.util.List;

public class PalestraForm {
    
    private int id;
    private String nome;
    private int duracao;
    private String dataHoraInicio;
    private int eventoId;
    private List<Integer> palestrantesIds = new ArrayList<>();

    public PalestraForm() {}

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getDuracao() { return duracao; }
    public String getDataHoraInicio() { return dataHoraInicio; }
    public int getEventoId() { return eventoId; }
    public List<Integer> getPalestrantesIds() { return palestrantesIds; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDuracao(int duracao) { this.duracao = duracao; }
    public void setDataHoraInicio(String dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }
    public void setEventoId(int eventoId) { this.eventoId = eventoId; }
    public void setPalestrantesIds(List<Integer> palestrantesIds) { this.palestrantesIds = palestrantesIds; }
}