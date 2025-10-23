package persistencias;

import java.util.List;

public interface IFactory<Entidade, DashboardDTO, PerfilDTO, OpcaoDTO, CriacaoDTO> {
	public abstract List<DashboardDTO> getDashboard(List<Entidade> entidades);
	public abstract List<OpcaoDTO> getOpcao(List<Entidade> entidades);
	public abstract PerfilDTO getPerfil(Entidade entidade);
	public abstract Entidade toEntidade(CriacaoDTO criacaoDTO);
}