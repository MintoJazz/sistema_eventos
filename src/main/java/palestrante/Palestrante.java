package palestrante;

import usuario.Usuario;

public class Palestrante extends Usuario {
    
    private String biografia;
    private int quantidadePalestras; // Novo campo (Read-only)

    public Palestrante() {
        super();
    }

    public Palestrante(int id, String nome, String cpf, String email, String senha, String biografia) {
        super(id, nome, cpf, email, senha);
        this.biografia = biografia;
    }

    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }

    public int getQuantidadePalestras() { return quantidadePalestras; }
    public void setQuantidadePalestras(int quantidadePalestras) { this.quantidadePalestras = quantidadePalestras; }
}