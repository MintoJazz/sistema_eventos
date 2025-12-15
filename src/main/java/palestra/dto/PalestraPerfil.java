package palestra.dto;

import java.util.ArrayList;
import java.util.List;

import persistencias.OpcaoDTO;

public class PalestraPerfil {
    
    private int id;
    private String nome;
    private int duracao;
    private String dataHora;
    private OpcaoDTO evento;
    private List<OpcaoDTO> palestrantes = new ArrayList<>();

    public PalestraPerfil() {}

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getDuracao() { return duracao; }
    public String getDataHora() { return dataHora; }
    public OpcaoDTO getEvento() { return evento; }
    public List<OpcaoDTO> getPalestrantes() { return palestrantes; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDuracao(int duracao) { this.duracao = duracao; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }
    public void setEvento(OpcaoDTO evento) { this.evento = evento; }
    public void setPalestrantes(List<OpcaoDTO> palestrantes) { this.palestrantes = palestrantes; }
}