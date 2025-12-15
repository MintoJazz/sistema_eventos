package palestrante.dto;

public class PalestranteDashboard {
    
    private int id;
    private String nome;
    private int quantidadePalestras;

    public PalestranteDashboard() {
    }

    public PalestranteDashboard(int id, String nome, int quantidadePalestras) {
        this.id = id;
        this.nome = nome;
        this.quantidadePalestras = quantidadePalestras;
    }

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

    public int getQuantidadePalestras() {
        return quantidadePalestras;
    }

    public void setQuantidadePalestras(int quantidadePalestras) {
        this.quantidadePalestras = quantidadePalestras;
    }
}