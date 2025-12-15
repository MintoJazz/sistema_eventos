package inscricao;

import java.time.LocalDateTime;

public class Inscricao {
    private int id;
    private int usuarioId;
    private int eventoId;
    private LocalDateTime dataHora;
    private double valor;
    private boolean pago;

    public Inscricao() {}

    public Inscricao(int id, int usuarioId, int eventoId, LocalDateTime dataHora, double valor, boolean pago) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.dataHora = dataHora;
        this.valor = valor;
        this.pago = pago;
    }

    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public int getEventoId() { return eventoId; }
    public LocalDateTime getDataHora() { return dataHora; }
    public double getValor() { return valor; }
    public boolean isPago() { return pago; }
    
    public void setId(int id) { this.id = id; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    public void setEventoId(int eventoId) { this.eventoId = eventoId; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public void setValor(double valor) { this.valor = valor; }
    public void setPago(boolean pago) { this.pago = pago; }
}