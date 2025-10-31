package evento.dto;

import java.util.Map;

public class EventoPerfilDTO {

    private Integer id;
    private String nome;
    private String local;
    private String dataInicioFormatada;
    private String dataFimFormatada;
    private String status;

    private Map<String, Object> detalhes;
    private Map<String, Object> metadados;

    private String nomeArquivoMaterial;
    private String tipoArquivoMaterial;
    private Long tamanhoArquivoMaterial;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        this.local= local;
    }

    public String getDataInicioFormatada() {
        return dataInicioFormatada;
    }

    public void setDataInicioFormatada(String dataInicioFormatada) {
        this.dataInicioFormatada = dataInicioFormatada;
    }

    public String getDataFimFormatada() {
        return dataFimFormatada;
    }

    public void setDataFimFormatada(String dataFimFormatada) {
        this.dataFimFormatada = dataFimFormatada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(Map<String, Object> detalhes) {
        this.detalhes = detalhes;
    }

    public Map<String, Object> getMetadados() {
        return metadados;
    }

    public void setMetadados(Map<String, Object> metadados) {
        this.metadados = metadados;
    }

    public String getNomeArquivoMaterial() {
        return nomeArquivoMaterial;
    }

    public void setNomeArquivoMaterial(String nomeArquivoMaterial) {
        this.nomeArquivoMaterial = nomeArquivoMaterial;
    }

    public String getTipoArquivoMaterial() {
        return tipoArquivoMaterial;
    }

    public void setTipoArquivoMaterial(String tipoArquivoMaterial) {
        this.tipoArquivoMaterial = tipoArquivoMaterial;
    }

    public Long getTamanhoArquivoMaterial() {
        return tamanhoArquivoMaterial;
    }

    public void setTamanhoArquivoMaterial(Long tamanhoArquivoMaterial) {
        this.tamanhoArquivoMaterial = tamanhoArquivoMaterial;
    }
}
