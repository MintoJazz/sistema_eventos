package negocio;

import java.sql.Date;
import java.time.LocalDate;

import persistencias.Formatadores;

public class Evento {

    private int id;
    private String nome, localidade, dataInicio, dataFim, status;
    private Date dataInicioDate, dataFimDate;
    
    public Evento(
        int id,
        String nome,
        Date dataInicio,
        Date dataFim,
        String localidade
    ) {
        this.setId(id);
        this.setNome(nome);
        this.setDataInicio(Formatadores.data2String(dataInicio));
        this.setDataInicioDate(dataInicio);
        this.setDataFim(Formatadores.data2String(dataFim));
        this.setDataFimDate(dataFim);
        this.setLocalidade(localidade);
        this.setStatus();
    }

    public int getId() {
        return id;
    } public String getNome() {
        return nome;
    } public String getDataInicio() {
        return dataInicio;
    } public String getDataFim() {
        return dataFim;
    } public String getLocalidade() {
        return localidade;
    } public Date getDataFimDate() {
        return dataFimDate;
    } public Date getDataInicioDate() {
        return dataInicioDate;
    } public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    } public void setNome(String nome) {
        this.nome = nome;
    } public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    } public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    } public void setLocalidade(String localidade) {
        this.localidade = localidade;
    } public void setDataFimDate(Date dataFimDate) {
        this.dataFimDate = dataFimDate;
    } public void setDataInicioDate(Date dataInicioDate) {
        this.dataInicioDate = dataInicioDate;
    }

    private void setStatus() {
        Date hoje = Date.valueOf(LocalDate.now());
        int distanciaInicio = dataInicioDate.compareTo(hoje), distanciaFim = dataFimDate.compareTo(hoje);
        if (distanciaInicio > 0) this.status = "Futuro";
        else if (distanciaFim >= 0) this.status = "Em Andamento";
        else this.status = "Encerrado";
    }
}
