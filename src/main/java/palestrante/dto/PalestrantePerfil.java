package palestrante.dto;

import java.util.ArrayList;
import java.util.List;

import palestra.dto.PalestraResumo;
import usuario.dto.UsuarioPerfil;

public class PalestrantePerfil extends UsuarioPerfil {
    
    private String biografia;
    private List<PalestraResumo> palestras = new ArrayList<>();

    public PalestrantePerfil() {
        super();
    }

    // --- Getters e Setters ---
    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }

    public List<PalestraResumo> getPalestras() { return palestras; }
    public void setPalestras(List<PalestraResumo> palestras) { this.palestras = palestras; }

}