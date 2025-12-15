package usuario.dto;

import java.util.ArrayList;
import java.util.List;

import evento.dto.EventoResumo;
import palestra.dto.PalestraResumo;

public class UsuarioPerfil {

    private int id;
    private String nome;
    private String cpf;
    private String email;
    
    // Inicialização direta para evitar NullPointerException no template
    private List<EventoResumo> eventos = new ArrayList<>();
    private List<PalestraResumo> palestras = new ArrayList<>();
    private List<EventoResumo> eventosOrganizados = new ArrayList<>();

    public UsuarioPerfil() {};

    // --- Getters ---
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public List<EventoResumo> getEventos() { return eventos; }
    public List<PalestraResumo> getPalestras() { return palestras; }
    public List<EventoResumo> getEventosOrganizados() { return eventosOrganizados; }

    // --- Setters ---
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setEmail(String email) { this.email = email; }
    public void setEventos(List<EventoResumo> eventos) { this.eventos = eventos; }
    public void setPalestras(List<PalestraResumo> palestras) { this.palestras = palestras; }
    public void setEventosOrganizados(List<EventoResumo> eventosOrganizados) { this.eventosOrganizados = eventosOrganizados; }
}