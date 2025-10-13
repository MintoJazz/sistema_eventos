package negocio;

import java.util.List;

public class Palestrante {
    private int id;
    private String nome, biografia, cpf;
    List<Palestra> palestras;

    public Palestrante (
        int id,
        String nome,
        String biografia,
        String cpf,
        List<Palestra> palestras
    ) {
        setId(id);
        setNome(nome);
        setCpf(cpf);
        setBiografia(biografia);
        setPalestras(palestras);
    }

    public Palestrante (
        int id,
        String nome,
        String biografia,
        String cpf
    ) {
        setId(id);
        setNome(nome);
        setCpf(cpf);
        setBiografia(biografia);
    }

    public int getId() {
        return id;
    } public String getNome() {
        return nome;
    } public String getCpf() {
        return cpf;
    } public String getBiografia() {
        return biografia;
    } public List<Palestra> getPalestras() {
        return palestras;
    }

    public void setId(int id) {
        this.id = id;
    } public void setNome(String nome) {
        this.nome = nome;
    } public void setBiografia(String biografia) {
        this.biografia = biografia;
    } public void setCpf(String cpf) {
        this.cpf = cpf;
    } public void setPalestras(List<Palestra> palestras) {
        this.palestras = palestras;
    }
}
