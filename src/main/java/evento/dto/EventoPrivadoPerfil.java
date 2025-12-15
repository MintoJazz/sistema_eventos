package evento.dto;

public class EventoPrivadoPerfil extends EventoPerfil {
    
    private String senhaAcesso;
    private String convidados;

    public EventoPrivadoPerfil() { super(); }

    public String getSenhaAcesso() { return senhaAcesso; }
    public String getConvidados() { return convidados; }

    public void setSenhaAcesso(String senhaAcesso) { this.senhaAcesso = senhaAcesso; }
    public void setConvidados(String convidados) { this.convidados = convidados; }

}