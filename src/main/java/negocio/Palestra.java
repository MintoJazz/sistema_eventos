package negocio;

import java.sql.Timestamp;

public class Palestra {
    private int id, duracao;
    private Timestamp inicioTimestamp;
    private String nome;
    private Evento evento;

    public Palestra(
        int id,
        String nome,
        Timestamp inicioTimestamp,
        int duracao
    ) {
        setId(id);
        setNome(nome);
        setDuracao(duracao);
        setInicioTimestamp(inicioTimestamp);
    }

    public Palestra(
        int id,
        String nome,
        Timestamp inicioTimestamp,
        int duracao,
        Evento evento
    ) {
        setId(id);
        setNome(nome);
        setDuracao(duracao);
        setInicioTimestamp(inicioTimestamp);
        setEvento(evento);
    }

    public int getId() {
        return id;
    } public int getDuracao() {
        return duracao;
    } public Evento getEvento() {
        return evento;
    } public Timestamp getInicioTimestamp() {
        return inicioTimestamp;
    } public String getNome() {
        return nome;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    } public void setEvento(Evento evento) {
        this.evento = evento;
    } public void setId(int id) {
        this.id = id;
    } public void setInicioTimestamp(Timestamp inicioTimestamp) {
        this.inicioTimestamp = inicioTimestamp;
    } public void setNome(String nome) {
        this.nome = nome;
    }
}
