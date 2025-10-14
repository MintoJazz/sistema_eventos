package negocio;

import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import persistencias.Formatadores;

public class Palestra {
    private int id, duracao;
    private Timestamp inicioTimestamp;
    private String nome;
    private Evento evento;
    private List<Palestrante> palestrantes;

    public Palestra(int id, String nome, Timestamp inicioTimestamp, int duracao, Evento evento, List<Palestrante> palestrantes) {
        this.id = id;
        this.nome = nome;
        this.inicioTimestamp = inicioTimestamp;
        this.duracao = duracao;
        this.evento = evento;
        this.palestrantes = palestrantes;

        if (this.palestrantes == null) {
            this.palestrantes = new ArrayList<>();
        }
    }

    public Palestra(String nome, Timestamp inicioTimestamp, int duracao, Evento evento, List<Palestrante> palestrantes) {
        this(0, nome, inicioTimestamp, duracao, evento, palestrantes);
    }

    public Palestra(int id, String nome, Timestamp inicioTimestamp, int duracao) {
        this(id, nome, inicioTimestamp, duracao, null, null);
    }

    public Palestra(int id, String nome, Timestamp inicioTimestamp, int duracao, Evento evento) {
        this(id, nome, inicioTimestamp, duracao, evento, null);
    }

    public int getId() {
        return id;
    }
    public int getDuracao() {
        return duracao;
    }
    public Evento getEvento() {
        return evento;
    }
    public Timestamp getInicioTimestamp() {
        return inicioTimestamp;
    }
    public String getNome() {
        return nome;
    }
    public List<Palestrante> getPalestrantes() {
        return palestrantes;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setInicioTimestamp(Timestamp inicioTimestamp) {
        this.inicioTimestamp = inicioTimestamp;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setPalestrantes(List<Palestrante> palestrantes) {
        this.palestrantes = palestrantes;
    }

    public String getInicioFormatado() {
        if (this.inicioTimestamp == null) {
            return "A definir";
        }
        return Formatadores.Timestamp2StringData(this.inicioTimestamp);
    }
}