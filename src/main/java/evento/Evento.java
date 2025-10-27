package evento;

import java.sql.Date;
import java.util.Map;

public class Evento {
    private final int id;
    private final String nome, local;
    private final Date dataInicio, dataFim;
    private final Map<String,Object> detalhes, metadados;
    private final byte[] material;

    public Evento(int id, String nome, String local, Date dataInicio, Date dataFim, Map<String,Object> detalhes, Map<String,Object> metadados, byte[] material) {
        this.id = id;
        this.nome = nome;
        this.local = local;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.detalhes = detalhes;
        this.metadados = metadados;
        this.material = material;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getLocal() {
        return local;
    }
    public Date getDataInicio() {
        return dataInicio;
    }
    public Date getDataFim() {
        return dataFim;
    }
    public Map<String,Object> getDetalhes() {
        return detalhes;
    }
    public Map<String,Object> getMetadados() {
        return metadados;
    }
    public byte[] getMaterial() {
        return material;
    }
}
