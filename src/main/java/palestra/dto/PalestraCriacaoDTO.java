package palestra.dto;

import java.util.List;

public class PalestraCriacaoDTO {
    private String nome, duracao, dataHoraInicio, eventoId;      
    private List<String> palestranteId; 

    public PalestraCriacaoDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(String dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public String getEventoId() {
        return eventoId;
    }

    public void setEventoId(String eventoId) {
        this.eventoId = eventoId;
    }

    public List<String> getPalestranteId() {
        return palestranteId;
    }

    public void setPalestranteId(List<String> palestranteId) {
        this.palestranteId = palestranteId;
    }
}