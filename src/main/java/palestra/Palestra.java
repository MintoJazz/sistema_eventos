package palestra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Palestra {
    
    private int id;
    private String nome;
    private int duracao;
    private LocalDateTime dataHoraInicio;
    private int eventoId;
    private List<Integer> palestrantesIds = new ArrayList<>();

    public Palestra() {
    }

    public Palestra(int id, String nome, int duracao, LocalDateTime dataHoraInicio, int eventoId) {
        this.id = id;
        this.nome = nome;
        this.duracao = duracao;
        this.dataHoraInicio = dataHoraInicio;
        this.eventoId = eventoId;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getDuracao() { return duracao; }
    public int getEventoId() { return eventoId; }
    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public List<Integer> getPalestrantesIds() { return palestrantesIds; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDuracao(int duracao) { this.duracao = duracao; }
    public void setEventoId(int eventoId) { this.eventoId = eventoId; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }
    public void setPalestrantesIds(List<Integer> palestrantesIds) { this.palestrantesIds = palestrantesIds; }
}