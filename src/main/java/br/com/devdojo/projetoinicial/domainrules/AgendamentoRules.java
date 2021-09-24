package br.com.devdojo.projetoinicial.domainrules;

import br.com.devdojo.projetoinicial.persistence.model.Agenda;
import br.com.devdojo.projetoinicial.persistence.model.dto.SearchDTO;
import br.com.devdojo.projetoinicial.predicate.CustomPredicate;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.devdojo.projetoinicial.utils.Functions.setTimeDateToFirstMinuteOfDay;
import static br.com.devdojo.projetoinicial.utils.Functions.setTimeDateToMaxMinuteOfDay;

/**
 * @author André Ribeiro, William Suane on 5/3/17.
 */
public class AgendamentoRules {
    public static List<SearchDTO> searchAgendaHorario(int consultorioId, int especialistaId, Agenda agenda){
        return ConsultaService.searchHorarios(consultorioId,
                especialistaId,
                setTimeDateToFirstMinuteOfDay(agenda.getDataInicio()),
                setTimeDateToMaxMinuteOfDay(agenda.getDataFinal()))
                .stream()
                .filter(CustomPredicate.distinctByKey(SearchDTO::getDiaInicio))
                .collect(Collectors.toList());
    }
}
