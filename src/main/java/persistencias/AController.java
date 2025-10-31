package persistencias;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.javalin.http.Context;

public abstract class AController <
	DashboardDTO,
	PerfilDTO,
	CriacaoDTO,
	OpcaoDTO,
	Entidade extends IGetId,
	DAO extends ADAO<Entidade>,
	Factory extends IFactory<Entidade, DashboardDTO, PerfilDTO, OpcaoDTO, CriacaoDTO>,
	Service extends AService<DashboardDTO, PerfilDTO, CriacaoDTO, OpcaoDTO, Entidade, DAO, Factory>
> {
	protected final String nomeTabela;
	protected final Service service;

	public AController(String nomeTabela, Service service) {
		this.nomeTabela = nomeTabela;
		this.service = service;
	}

	public void dashboard(Context ctx) throws SQLException {
		Map<String,Object> model = new HashMap<>();
		List<DashboardDTO> lista = this.service.listar();
		model.put("entidades",lista);
		ctx.render("/templates/dashboard-" + this.nomeTabela + ".html", model);
	}

	public void formulario(Context ctx) throws SQLException {
		Map<String,Object> model = this.service.elementosFormulario();
		Map<String,String> erros = ctx.sessionAttribute("erros");
		Map<String,String> input = ctx.sessionAttribute("inputs");

		if (erros != null) model.put("erros", erros);
		if (input != null) model.put("input", input);

		ctx.render("/templates/formulario-" + this.nomeTabela + ".html", model);
	}

	protected abstract CriacaoDTO getCriacaoDTO(Context ctx);

	public void criacao(Context ctx) throws SQLException {
		CriacaoDTO criacaoDTO = this.getCriacaoDTO(ctx);
		Map<String, String> erros = this.service.validador(criacaoDTO);

		if (erros.isEmpty()) {
			this.service.salvar(criacaoDTO);
			ctx.redirect("/");
		}
		else {
			ctx.sessionAttribute("erros", erros);
			ctx.sessionAttribute("input", criacaoDTO);
			ctx.redirect("/evento/nova");
		}
	}

	public void perfil(Context ctx, String coluna) throws SQLException {
		Map<String,Object> model = new HashMap<>();
		PerfilDTO perfil = this.service.getPerfil(ctx.pathParam(coluna), coluna);

		if (perfil == null) { 
			ctx.status(404); 
			return; 
		}

		model.put("perfil", perfil);
		ctx.render("/templates/perfil-" + this.nomeTabela + ".html", model);
	}
}