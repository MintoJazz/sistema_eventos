package evento.privado;

import java.time.LocalDate;
import evento.Evento;

public class EventoPrivado extends Evento {
    private String senhaAcesso;
    private String convidados; 

    public EventoPrivado(int id, String nome, LocalDate dataInicio, LocalDate dataFim, String localidade, int organizadorId, String senhaAcesso, String convidados) {
        // O último argumento 'true' indica que é privado
        super(id, nome, dataInicio, dataFim, localidade, organizadorId, true);
        this.senhaAcesso = senhaAcesso;
        this.convidados = convidados;
    }

    public EventoPrivado() { 
        super();
        this.setPrivado(true); // Garante que seja true no construtor vazio também
    }

    public String getSenhaAcesso() { return senhaAcesso; }
    public String getConvidados() { return convidados; }
    
    public void setSenhaAcesso(String senhaAcesso) { this.senhaAcesso = senhaAcesso; }
    public void setConvidados(String convidados) { this.convidados = convidados; }
}