package inscricao;

import evento.Evento;
import participante.Participante;
import persistencias.IGetId;

public class Inscricao implements IGetId{
    private final int id, eventoId, participanteId;

    public Inscricao(int id, int evento_id, int participante_id) {
        this.id = id;
        this.eventoId = evento_id;
        this.participanteId = participante_id;
    }

    public int getId() {
        return id;
    }

    public int getEventoId() {
        return eventoId;
    }

    public int getParticipanteId() {
        return participanteId;
    }
}
