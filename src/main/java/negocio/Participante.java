package negocio;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistencias.Formatadores;

public class Participante {
    private int id;
    private String nome, cpf, email, dataNascimento;
    private Date dataNascimentoDate;
    private List<Evento> eventos = new ArrayList<>();

    public Participante (
        int id,
        String nome,
        String cpf,
        String email,
        Date dataNascimento,
        List<Evento> eventos
    ) {
        this.setId(id);
        this.setNome(nome);
        this.setDataNascimento(Formatadores.data2String(dataNascimento));
        this.setDataNascimentoDate(dataNascimento);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setEventos(eventos);
    }

    public Participante(
        int id,
        String nome,
        String cpf,
        String email,
        Date dataNascimento
    ) throws SQLException {
        this.setId(id);
        this.setNome(nome);
        this.setDataNascimento(Formatadores.data2String(dataNascimento));
        this.setDataNascimentoDate(dataNascimento);
        this.setCpf(cpf);
        this.setEmail(email);
    }

    public int getId() {
        return id;
    } public String getNome() {
        return nome;
    } public Date getDataNascimentoDate() {
        return dataNascimentoDate;
    } public String getCpf() {
        return cpf;
    } public String getEmail() {
        return email;
    } public String getDataNascimento() {
        return dataNascimento;
    } public List<Evento> getEventos() {
        return this.eventos;
    }

    public void setId(int id) {
        this.id = id;
    } public void setNome(String nome) {
        this.nome = nome;
    } public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    } public void setCpf(String cpf) {
        this.cpf = cpf;
    } public void setEmail(String email) {
        this.email = email;
    } public void setDataNascimentoDate(Date dataNascimentoDate) {
        this.dataNascimentoDate = dataNascimentoDate;
    } public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
}
