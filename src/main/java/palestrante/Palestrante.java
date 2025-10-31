package palestrante;

import persistencias.IGetId;

public class Palestrante implements IGetId {
    private final int id;
    private final String nome, biografia, cpf;
    
    public Palestrante(int id, String nome, String biografia, String cpf) {
        this.id = id;
        this.nome = nome;
        this.biografia = biografia;
        this.cpf = cpf;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getBiografia() {
        return biografia;
    }
    public String getCpf() {
        return cpf;
    }
}
