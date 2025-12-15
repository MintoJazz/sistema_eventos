package example;

import java.util.HashMap;
import java.util.Map;

import exceptions.RecursoNaoEncontradoException;
import persistencias.ControllerCRUD;

// --- Imports de Usuario ---
import usuario.UsuarioController;
import usuario.UsuarioDAO;
import usuario.UsuarioFactory;
import usuario.UsuarioService;
import usuario.parser.UsuarioToForm;
import usuario.parser.UsuarioToOpcao;
import usuario.parser.UsuarioToPerfil;

// --- Imports de Evento (Público) ---
import evento.EventoController;
import evento.EventoDAO;
import evento.EventoFactory;
import evento.EventoService;
import evento.parser.EventoToDashboard;
import evento.parser.EventoToForm;
import evento.parser.EventoToOpcao;
import evento.parser.EventoToPerfil;
import evento.parser.EventoToResumo;

// --- Imports de Evento Privado ---
import evento.privado.EventoPrivadoController;
import evento.privado.EventoPrivadoDAO;
import evento.privado.EventoPrivadoFactory;
import evento.privado.EventoPrivadoService;
import evento.parser.EventoPrivadoToForm;
import evento.parser.EventoPrivadoToPerfil;

// --- Imports de Palestra ---
import palestra.PalestraController;
import palestra.PalestraDAO;
import palestra.PalestraFactory;
import palestra.PalestraService;
import palestra.parser.PalestraToCronogramaItem;
import palestra.parser.PalestraToDashboard;
import palestra.parser.PalestraToForm;
import palestra.parser.PalestraToPerfil;
import palestra.parser.PalestraToResumo;

// --- Imports de Palestrante ---
import palestrante.PalestranteController;
import palestrante.PalestranteDAO;
import palestrante.PalestranteFactory;
import palestrante.PalestranteService;
import palestrante.parser.PalestranteToDashboard;
import palestrante.parser.PalestranteToForm;
import palestrante.parser.PalestranteToOpcao;
import palestrante.parser.PalestranteToPerfil;

// --- Imports de Inscrição ---
import inscricao.InscricaoController;
import inscricao.InscricaoDAO;
import inscricao.InscricaoFactory;
import inscricao.InscricaoService;
import inscricao.parser.InscricaoToForm;

public class Injector {
    private static final Map<String, ControllerCRUD<?,?,?,?>> controle = new HashMap<>(); 
    
    static {
        
        // =============================================================================================
        // 1. INSTANCIAÇÃO DOS MODULOS (Factories, DAOs e Services)
        // =============================================================================================

        // --- Módulo: USUÁRIO ---
        UsuarioFactory usuarioFactory = new UsuarioFactory();
        usuarioFactory.registrar(new UsuarioToForm());
        usuarioFactory.registrar(new UsuarioToOpcao());
        usuarioFactory.registrar(new UsuarioToPerfil());
        UsuarioDAO usuarioDAO = new UsuarioDAO("usuario");
        UsuarioService usuarioService = new UsuarioService(usuarioDAO, usuarioFactory);

        // --- Módulo: EVENTO (Público/Genérico) ---
        EventoFactory eventoFactory = new EventoFactory();
        eventoFactory.registrar(new EventoToDashboard());
        eventoFactory.registrar(new EventoToForm());
        eventoFactory.registrar(new EventoToPerfil());
        eventoFactory.registrar(new EventoToResumo());
        eventoFactory.registrar(new EventoToOpcao());
        EventoDAO eventoDAO = new EventoDAO("evento");
        EventoService eventoService = new EventoService(eventoDAO, eventoFactory);

        // --- Módulo: PALESTRA ---
        PalestraFactory palestraFactory = new PalestraFactory();
        palestraFactory.registrar(new PalestraToCronogramaItem());
        palestraFactory.registrar(new PalestraToDashboard());
        palestraFactory.registrar(new PalestraToForm());
        palestraFactory.registrar(new PalestraToPerfil());
        palestraFactory.registrar(new PalestraToResumo());
        PalestraDAO palestraDAO = new PalestraDAO("palestra");
        PalestraService palestraService = new PalestraService(palestraDAO, palestraFactory);

        // --- Módulo: PALESTRANTE ---
        PalestranteFactory palestranteFactory = new PalestranteFactory();
        palestranteFactory.registrar(new PalestranteToDashboard());
        palestranteFactory.registrar(new PalestranteToForm());
        palestranteFactory.registrar(new PalestranteToPerfil());
        palestranteFactory.registrar(new PalestranteToOpcao());
        PalestranteDAO palestranteDAO = new PalestranteDAO("perfil_palestrante");
        PalestranteService palestranteService = new PalestranteService(palestranteDAO, palestranteFactory);

        // --- Módulo: INSCRIÇÃO ---
        InscricaoFactory inscricaoFactory = new InscricaoFactory();
        inscricaoFactory.registrar(new InscricaoToForm());
        InscricaoDAO inscricaoDAO = new InscricaoDAO();
        InscricaoService inscricaoService = new InscricaoService(inscricaoDAO, inscricaoFactory);

        // --- Módulo: EVENTO PRIVADO (Extensão) ---
        EventoPrivadoFactory privadoFactory = new EventoPrivadoFactory();
        privadoFactory.registrar(new EventoPrivadoToForm());
        privadoFactory.registrar(new EventoPrivadoToPerfil());
        EventoPrivadoDAO privadoDAO = new EventoPrivadoDAO();
        EventoPrivadoService privadoService = new EventoPrivadoService(privadoDAO, privadoFactory);


        // =============================================================================================
        // 2. INJEÇÃO DE DEPENDÊNCIAS (Cross-Wiring)
        // =============================================================================================
        
        // EventoService precisa acessar outros serviços para montar o Perfil do Evento
        eventoService.setPalestraService(palestraService);
        eventoService.setUsuarioService(usuarioService);
        // EventoPrivadoService reusa lógica do EventoService e acessa dependências
        privadoService.setDependencias(eventoService, palestraService, usuarioService);

        // PalestraService precisa validar dono (Evento) e listar usuários (Palestrante/Usuario)
        palestraService.setEventoService(eventoService);
        palestraService.setPalestranteService(palestranteService);
        palestraService.setUsuarioService(usuarioService);

        // PalestranteService precisa listar palestras no perfil (Agenda)
        palestranteService.setPalestraService(palestraService);
        palestranteService.setUsuarioService(usuarioService);

        // UsuarioService precisa montar o "Meu Perfil" (Inscrições, Palestras, Eventos Criados)
        usuarioService.setEventoService(eventoService);
        usuarioService.setPalestraService(palestraService);

        // =============================================================================================
        // 3. REGISTRO DE CONTROLLERS (Rotas)
        // =============================================================================================
        
        controle.put("usuario", new UsuarioController(usuarioService));
        controle.put("evento", new EventoController(eventoService));
        controle.put("palestra", new PalestraController(palestraService));
        controle.put("palestrante", new PalestranteController(palestranteService));
        
        // Novos Controllers
        controle.put("inscricao", new InscricaoController(inscricaoService));
        controle.put("evento-privado", new EventoPrivadoController(privadoService));
    }

    public static ControllerCRUD<?,?,?,?> verificarController(String tabela) {
        if (!controle.containsKey(tabela)) 
            throw new RecursoNaoEncontradoException("Recurso não encontrado para a tabela ou rota: " + tabela);
        
        return controle.get(tabela);
    }
}