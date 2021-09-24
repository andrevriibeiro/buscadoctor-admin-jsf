package br.com.devdojo.projetoinicial.service.agenda;

import br.com.devdojo.projetoinicial.persistence.model.Agenda;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.Functions;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author André Ribeiro, William Suane on 3/30/17.
 */
public class AgendaService {
    private static final String SEARCH = ConstantsUtil.URL_API + "/agendas/search";
    private static final String GET = ConstantsUtil.URL_API + "/agendas/";
    private static final String BATCH_CREATE = ConstantsUtil.URL_API + "/agendas/batchCreate";
    private static final String BATCH_UPDATE = ConstantsUtil.URL_API + "/agendas/batchUpdate";
    private static final String BATCH_DELETE = ConstantsUtil.URL_API + "/agendas/batchDelete";

    public static List<Agenda> getAllAgendas(List<String> consultoriosIdList, int especialistaId, int tipoAgendaId, Date inicio, Date fim) {
        URLUtil urlUtil = getUrlUtil(consultoriosIdList, especialistaId, tipoAgendaId, inicio, fim);
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "agendas"), new TypeReference<List<Agenda>>() {
        });
    }

    public static List<Agenda> getAllAgendas(List<String> consultoriosIdList, int especialistaId, int tipoAgendaId, Date inicio, Date fim, List<Agenda> agendaList) {
        URLUtil urlUtil = getUrlUtil(consultoriosIdList, especialistaId, tipoAgendaId, inicio, fim)
                .addParam(agendaList.stream().map(a -> a.getId().toString()).collect(Collectors.joining(",")), "agendas");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "agendas"), new TypeReference<List<Agenda>>() {
        });
    }

    public static Agenda getAgenda(int agendaId) {
        JSONObject objConsultorio = new JSONObject(JSONUtil.getEntityFromAPI(GET+agendaId, String.class)).getJSONObject("agenda");
        return JSONUtil.extractEntityFromJSON(objConsultorio.toString(), new TypeReference<Agenda>() {
        });
    }

    private static URLUtil getUrlUtil(List<String> consultoriosIdList, int especialistaId, int tipoAgendaId, Date inicio, Date fim) {
        return new URLUtil(SEARCH)
                .addParam(consultoriosIdList.stream().collect(Collectors.joining(",")), "consultorios")
                .addParam(especialistaId, "especialista")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(inicio), "inicio")
                .addParam(Functions.formateDateToStringLastMinuteOfDay(fim), "fim")
                .addParam(tipoAgendaId, "tipo");
    }

    public static int create(List<Agenda> agenda) {
        return JSONUtil.sendListOfEntityReturningStatusCode(agenda, BATCH_CREATE);
    }

    public static int update(List<Agenda> agendaList) {
        return JSONUtil.sendListOfEntityReturningStatusCode(agendaList, BATCH_UPDATE);
    }

    public static int delete(List<Agenda> agendaList) {
        return JSONUtil.deleteListOfEntityReturningStatusCode(agendaList, BATCH_DELETE);
    }
}
