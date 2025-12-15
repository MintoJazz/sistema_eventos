package persistencias;

public interface IParser<Entidade, DTO> {
    DTO toDTO(Entidade entidade);
    Entidade toEntidade(DTO dto);
    Class<DTO> getDTOClass();
}
