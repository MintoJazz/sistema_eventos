package evento.dto;

public class EventoOpcaoDTO {
    private final int id;
    private final String nome;

    public EventoOpcaoDTO(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
}
