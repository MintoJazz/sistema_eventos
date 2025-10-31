package participante;

import java.time.LocalDate;

import persistencias.IGetId;

public class Participante implements IGetId{
    private final int id;
    private final String nome, email, cpf;
    private final LocalDate dataNascimento;

    public Participante(int id, String nome, String email, String cpf, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }
    
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public String getCpf() {
        return cpf;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    
}
