package evento.dto;

public class EventoDashboardDTO {
    private int id;
    private String nome, dataFim, dataInicio, local, status;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDataFim() {
        return dataFim;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getLocal() {
        return local;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
