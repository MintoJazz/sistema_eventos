package negocio;

import java.sql.Timestamp;

import persistencias.Formatadores;

public class Inscricao {
    int id; 
    Double valor;
    Evento evento;
    Participante participante;
    Timestamp dataHora;
    String data;
    boolean pago;

    public Inscricao (
        int id,
        Evento evento,
        Participante participante,
        Timestamp dataHora,
        Double valor,
        boolean pago
    ) {
        this.setId(id);
        this.setEvento(evento);
        this.setParticipante(participante);
        this.setDataHora(dataHora);
        this.setValor(valor);
        this.setPago(pago);
        this.setData();
    }

    public int getId() {
        return id;
    } public Evento getEvento() {
        return evento;
    } public Participante getParticipante() {
        return participante;
    } public Timestamp getDataHora() {
        return dataHora;
    } public Double getValor() {
        return valor;
    }

    public void setId(int id) {
        this.id = id;
    } public void setEvento(Evento evento) {
        this.evento = evento;
    } public void setParticipante(Participante participante) {
        this.participante = participante;
    } public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    } public void setValor(Double valor) {
        this.valor = valor;
    } public void setPago(boolean pago) {
        this.pago = pago;
    } public void setData() {
        this.data = Formatadores.Timestamp2StringData(this.dataHora);
    }
}
