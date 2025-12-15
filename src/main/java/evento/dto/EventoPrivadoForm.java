package evento.dto;

public class EventoPrivadoForm extends EventoForm {
    
    private String senhaAcesso;
    private String convidados; 

    public EventoPrivadoForm() { super(); }

    public String getSenhaAcesso() { return senhaAcesso; }
    public String getConvidados() { return convidados; }
    
    public void setSenhaAcesso(String senhaAcesso) { this.senhaAcesso = senhaAcesso; }
    public void setConvidados(String convidados) { this.convidados = convidados; }

}