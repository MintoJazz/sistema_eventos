package evento.dto;

public class EventoDashboardDTO {
    private final int id;
    private final String nome, dataFim, dataInicio, local, status;

    public EventoDashboardDTO(int id, String nome, String dataFim, String dataInicio, String local, String status) {
        this.id = id;
        this.nome = nome;
        this.dataFim = dataFim;
        this.dataInicio = dataInicio;
        this.local = local;
        this.status = status;
    }

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
}
