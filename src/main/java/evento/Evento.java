package evento;

import java.sql.Date;

public class Evento {
    private int id;
    private String nome, local, status;
    private Date dataInicio, dataFim;

    public Evento(
        int id,
        String nome, 
        Date dataInicio, 
        Date dataFim, 
        String local, 
        String status
    ) {
        setId(id);
        setNome(nome);
        setDataInicio(dataInicio);
        setDataFim(dataFim);
        setLocal(local);
        setStatus(status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
}
