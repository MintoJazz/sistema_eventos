package evento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import evento.dto.EventoCriacaoDTO;
import evento.dto.EventoDashboardDTO;
import evento.dto.EventoOpcaoDTO;
import evento.dto.EventoPerfilDTO;
import persistencias.Formatadores;
import persistencias.IFactory;

public class EventoFactory implements IFactory <
    Evento, EventoDashboardDTO, EventoPerfilDTO, EventoOpcaoDTO, EventoCriacaoDTO
> {

    @Override public List<EventoDashboardDTO> getDashboard(List<Evento> entidades) {
        List<EventoDashboardDTO> eventos = new ArrayList<>();
        
        for (Evento evento : entidades) {
            EventoDashboardDTO dashboardDTO = new EventoDashboardDTO();
            dashboardDTO.setNome(evento.getNome());
            dashboardDTO.setId(evento.getId());
            dashboardDTO.setLocal(evento.getLocal());

            LocalDate dataInicio = evento.getDataInicio();
            LocalDate dataFim = evento.getDataFim();
            LocalDate agora = LocalDate.now();

            if (dataInicio.isAfter(agora)) dashboardDTO.setStatus("Futuro");
            else if (dataFim.isAfter(agora) || dataFim.equals(agora)) dashboardDTO.setStatus("Em andamento");
            else dashboardDTO.setStatus("Encerrado");

            dashboardDTO.setDataFim(Formatadores.data2String(dataFim));
            if (evento.getDataInicio() != null) dashboardDTO.setDataInicio(Formatadores.data2String(dataInicio));

            eventos.add(dashboardDTO);
        }

        return eventos;
    }

    @Override public List<EventoOpcaoDTO> getOpcao(List<Evento> entidades) {
        List<EventoOpcaoDTO> eventos = new ArrayList<>();
        
        for (Evento evento : entidades) {
            EventoOpcaoDTO opcaoDTO = new EventoOpcaoDTO();

            opcaoDTO.setId(evento.getId());
            opcaoDTO.setNome(evento.getNome());

            eventos.add(opcaoDTO);
        }

        return eventos;
    }

    @Override public EventoPerfilDTO getPerfil(Evento entidade) {
        EventoPerfilDTO perfilDTO = new EventoPerfilDTO();

        if (entidade == null) {
            return null; // Or handle as appropriate
        }

        perfilDTO.setId(entidade.getId());
        perfilDTO.setNome(entidade.getNome());
        perfilDTO.setLocal(entidade.getLocal());

        if (entidade.getDataInicio() != null) perfilDTO.setDataInicioFormatada(Formatadores.data2String(entidade.getDataInicio())); 
        if (entidade.getDataFim() != null) perfilDTO.setDataFimFormatada(Formatadores.data2String(entidade.getDataFim()));

        perfilDTO.setDetalhes(entidade.getDetalhes()); 
        perfilDTO.setMetadados(entidade.getMetadados());

        Map<String, Object> metadados = entidade.getMetadados();
        if (metadados != null) {
            perfilDTO.setNomeArquivoMaterial((String) metadados.getOrDefault("original_filename", null));
            perfilDTO.setTipoArquivoMaterial((String) metadados.getOrDefault("mime_type", null));
            Object sizeObj = metadados.getOrDefault("file_size_bytes", null); 
            if (sizeObj instanceof Number) {
            perfilDTO.setTamanhoArquivoMaterial(((Number) sizeObj).longValue());
            }
        }

        return perfilDTO;
    }

    @Override public Evento toEntidade(EventoCriacaoDTO criacaoDTO) {
        String nome = criacaoDTO.getNome();
        String local = criacaoDTO.getLocal();
        LocalDate dataInicio = LocalDate.parse(criacaoDTO.getDataInicio());
        LocalDate dataFim = null;

        if (!criacaoDTO.getDataFim().equals("")) dataFim = LocalDate.parse(criacaoDTO.getDataFim());

        //TODO: converter String (metadados, detalhes) -> Map<String,Object>
        byte[] material = criacaoDTO.getMaterial();

        return new Evento(0, nome, local, dataInicio, dataFim, null, null, material);
    }

}
