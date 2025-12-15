package persistencias;

import java.util.HashMap;
import java.util.Map;

public class AFactory<Entidade> {
    private Map<Class<?>, IParser<Entidade, ?>> parsers = new HashMap<>();

    public <DTO> void registrar(IParser<Entidade, DTO> parser) {
        parsers.put(parser.getDTOClass(), parser);
    }

    @SuppressWarnings("unchecked") public <DTO> DTO toDTO(Entidade entidade, Class<DTO> classDTO) {
        IParser<Entidade, DTO> parser = (IParser<Entidade, DTO>) parsers.get(classDTO);

        if (parser == null) throw new IllegalArgumentException("Nenhuma estratégia definida na entidade " + entidade.getClass().getName() + " para o DTO: " + classDTO.getClass().getName());

        return parser.toDTO(entidade);
    }

    @SuppressWarnings("unchecked") public <DTO> Entidade toEntidade(DTO dto) {
        IParser<Entidade, DTO> parser = (IParser<Entidade, DTO>) parsers.get(dto.getClass());
        if (parser == null) throw new IllegalArgumentException("Nenhuma estratégia definida para o DTO: " + dto.getClass().getName());

        return parser.toEntidade(dto);
    }

    public void setParsers(Map<Class<?>, IParser<Entidade, ?>> parsers) {
        this.parsers = parsers;
    }
}
