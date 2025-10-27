package evento.dto;

public class EventoCriacaoDTO {
    private String nome, dataFim, dataInicio, local, metadados, detalhes;
    private byte[] material;

    // public EventoCriacaoDTO(String nome, String dataFim, String dataInicio, String local, String metadados, String detalhes, byte[] material) {
    //     this.nome = nome;
    //     this.dataFim = dataFim;
    //     this.dataInicio = dataInicio;
    //     this.local = local;
    //     this.metadados = metadados;
    //     this.detalhes = detalhes;
    //     this.material = material;
    // }

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
    public String getMetadados() {
        return metadados;
    }
    public String getDetalhes() {
        return detalhes;
    }
    public byte[] getMaterial() {
        return material;
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
    public void setMetadados(String metadados) {
        this.metadados = metadados;
    }
    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
    public void setMaterial(byte[] material) {
        this.material = material;
    }
}
