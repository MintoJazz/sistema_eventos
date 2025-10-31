package palestra;

import java.time.LocalDateTime;

import persistencias.IGetId;

public class Palestra implements IGetId{
    private final int id, duracao, eventoId;
    private final String nome;
    private final LocalDateTime dataHoraInicio;

    public Palestra(int id, int duracao, int eventoId, String nome, LocalDateTime dataHoraInicio) {
        this.id = id;
        this.duracao = duracao;
        this.eventoId = eventoId;
        this.nome = nome;
        this.dataHoraInicio = dataHoraInicio;
    }
    
    public int getId() {
        return id;
    }
    public int getDuracao() {
        return duracao;
    }
    public String getNome() {
        return nome;
    }
    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public int getEventoId() {
        return eventoId;
    }
}
