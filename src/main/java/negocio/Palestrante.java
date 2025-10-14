package negocio;

import java.util.List;

public class Palestrante {
    private int id;
    private String nome, biografia, cpf;
    private int nroPalestras;
    private List<Palestra> palestras; 
    
    public Palestrante(int id, String nome, String biografia, String cpf, int nroPalestras) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.biografia = biografia;
        this.nroPalestras = nroPalestras;
    }
    
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getBiografia() { return biografia; }
    public int getNroPalestras() { return nroPalestras; }
    public List<Palestra> getPalestras() { return palestras; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setBiografia(String biografia) { this.biografia = biografia; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setNroPalestras(int nroPalestras) { this.nroPalestras = nroPalestras; }
    public void setPalestras(List<Palestra> palestras) { this.palestras = palestras; }
}