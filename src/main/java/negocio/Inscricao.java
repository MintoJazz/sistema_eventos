package negocio;

import java.sql.Timestamp;
import persistencias.Formatadores;

public class Inscricao {
    private int id; 
    private Double valor;
    private Evento evento;
    private Participante participante;
    private Timestamp dataHora;
    private boolean pago;

    public Inscricao(int id, Evento evento, Participante participante, Timestamp dataHora, Double valor, boolean pago) {
        this.id = id;
        this.evento = evento;
        this.participante = participante;
        this.dataHora = dataHora;
        this.valor = valor;
        this.pago = pago;
    }

    public Inscricao(Evento evento, Participante participante, Double valor, boolean pago) {
        this(0, evento, participante, null, valor, pago); 
    }

    public int getId() { return id; }
    public Evento getEvento() { return evento; }
    public Participante getParticipante() { return participante; }
    public Timestamp getDataHora() { return dataHora; }
    public Double getValor() { return valor; }
    public boolean isPago() { return pago; }

    public String getDataFormatada() {
        if (this.dataHora == null) return "N/D";
        return Formatadores.Timestamp2StringData(this.dataHora);
    }

    public void setId(int id) { this.id = id; }
    public void setEvento(Evento evento) { this.evento = evento; }
    public void setParticipante(Participante participante) { this.participante = participante; }
    public void setDataHora(Timestamp dataHora) { this.dataHora = dataHora; }
    public void setValor(Double valor) { this.valor = valor; }
    public void setPago(boolean pago) { this.pago = pago; }
}