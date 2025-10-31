package participante.dto;

import java.util.ArrayList;
import java.util.List;

import evento.dto.EventoDashboardDTO;

public class ParticipantePerfilDTO {
    private int id;
    private String nome, cpf, email, dataNascimento;
    private List<EventoDashboardDTO> eventos = new ArrayList<>();
    
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
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    public List<EventoDashboardDTO> getEventos() {
        return eventos;
    }
    public void setEventos(List<EventoDashboardDTO> eventos) {
        this.eventos = eventos;
    }
}
