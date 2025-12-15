package persistencias;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.javalin.http.Context;
import util.Reflexao;

public abstract class ControllerCRUD<Dashboard, Perfil, Form, Service extends ServiceCRUD<?,Perfil,Form,?,?>> {
    protected final Service service;
    
    public ControllerCRUD(Service service) {
        this.service = service;
    }

    protected abstract String getRole(Context ctx);

    public void perfil(Context ctx) {
        String role = getRole(ctx);
        int id = Integer.parseInt(ctx.pathParam("id")); 
        Perfil perfil = this.service.getPerfil(role, id);
        
        Map<String, Object> model = new HashMap<>();
        model.put("evento", perfil);
        
        model.put("role", role); 
        if (role.equals("app_admin")) model.put("menuAdmin", true);

        ctx.render("/templates/perfil-" + ctx.pathParam("tabela") + ".html", model);
    }

    public void dashboard(Context ctx) {
        String role = getRole(ctx); 
        List<Dashboard> lista = this.service.getAll(role, Reflexao.getGenericTypeArgument(getClass(), 0));
        
        Map<String, Object> model = new HashMap<>();
        model.put("lista", lista);
        model.put("role", role);
        
        ctx.render("/templates/dashboard-" + ctx.pathParam("tabela") + ".html", model);
    }

    public void formularioCriar(Context ctx) {
        String role = getRole(ctx);
            
        Map<String,Object> model = this.service.elementosFormulario(role);
        
        model.put("menuAdmin", role.equals("app_admin") || role.equals("app_organizer"));

        ctx.render("/templates/criar-" + ctx.pathParam("tabela") + ".html", model);
    }
    
    public void formularioAtualizar(Context ctx) {
        String role = getRole(ctx);
            
        Map<String,Object> model = this.service.elementosFormulario(role);
        
        int id = Integer.parseInt(ctx.pathParam("id"));
        Form form = service.getOne(role, id, Reflexao.getGenericTypeArgument(getClass(), 2));
        
        model.put("entidade", form);
        
        model.put("menuAdmin", role.equals("app_admin") || role.equals("app_organizer"));

        ctx.render("/templates/atualizar-" + ctx.pathParam("tabela") + ".html", model);
    }

    public void criar(Context ctx) {
        String role = getRole(ctx);
        Form form = ctx.bodyAsClass(Reflexao.getGenericTypeArgument(getClass(), 2));
        Map<String, String> erros = this.service.validador(form);

        if (!erros.isEmpty()) ctx.status(400).json(erros);
        else {
            int novoId = this.service.create(role, form);
            Map<String, Object> resposta = new HashMap<>();

            resposta.put("mensagem", "Recurso criado com sucesso!");
            resposta.put("id", novoId);

            resposta.put("redirectUrl", "/read/" + ctx.pathParam("tabela") + "/id/" + novoId); 
            
            ctx.status(201).json(resposta);
        }
    }

    public void atualizar(Context ctx) {
        String role = getRole(ctx);
        Form form = ctx.bodyAsClass(Reflexao.getGenericTypeArgument(getClass(), 2));
        this.service.update(role, form);

        Map<String, Object> resposta = new HashMap<>();

        resposta.put("mensagem", "Recurso atualizado com sucesso!");
        resposta.put("id", ctx.pathParam("id"));

        resposta.put("redirectUrl", "/read/" + ctx.pathParam("tabela") + "/id/" + resposta.get("id")); 
        
        ctx.status(200).json(resposta);
    }

    public void excluir(Context ctx) {
        String role = getRole(ctx);
        this.service.delete(role, ctx.pathParam("id"));
    }
}