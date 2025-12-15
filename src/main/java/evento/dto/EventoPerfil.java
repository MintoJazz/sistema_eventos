package evento.dto;

import java.util.ArrayList;
import java.util.List;
import palestra.dto.CronogramaItem;
import persistencias.OpcaoDTO;

public class EventoPerfil {
    
    private int id;
    private String nome;
    private String dataInicio;
    private String dataFim;
    private String localidade;
    private boolean temArquivo;
    private String nomeArquivo;

    private OpcaoDTO organizador;
    private List<CronogramaItem> cronograma = new ArrayList<>();

    public EventoPerfil() {}

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDataInicio() { return dataInicio; }
    public String getDataFim() { return dataFim; }
    public String getLocalidade() { return localidade; }
    public OpcaoDTO getOrganizador() { return organizador; }
    public List<CronogramaItem> getCronograma() { return cronograma; }
    public boolean isTemArquivo() { return temArquivo; }
    public String getNomeArquivo() { return nomeArquivo; }
    
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }
    public void setLocalidade(String localidade) { this.localidade = localidade; }
    public void setCronograma(List<CronogramaItem> cronograma) { this.cronograma = cronograma; }
    public void setOrganizador(OpcaoDTO organizador) { this.organizador = organizador; }
    public void setTemArquivo(boolean temArquivo) { this.temArquivo = temArquivo; }
    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }
}