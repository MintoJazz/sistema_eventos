package inscricao.dto;

public class InscricaoForm {
    
    private int id;
    private int eventoId;
    private int usuarioId;
    private String credencial;

    public InscricaoForm() {}

    public int getId() { return id; }
    public int getEventoId() { return eventoId; }
    public int getUsuarioId() { return usuarioId; }
    public String getCredencial() { return credencial; }

    public void setId(int id) { this.id = id; }
    public void setEventoId(int eventoId) { this.eventoId = eventoId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public void setCredencial(String credencial) { this.credencial = credencial; }
    
}