package palestrante.dto;

public class PalestranteForm {
    private int usuarioId;
    private String biografia;

    public PalestranteForm() {}

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }
}