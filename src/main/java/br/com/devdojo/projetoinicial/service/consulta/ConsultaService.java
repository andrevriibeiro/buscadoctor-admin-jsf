package br.com.devdojo.projetoinicial.service.consulta;

import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.dto.ConsultaDTO;
import br.com.devdojo.projetoinicial.persistence.model.dto.SearchDTO;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.Functions;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author André Ribeiro, William Suane on 4/4/17.
 */
public class ConsultaService {
    private static final String SEARCH = ConstantsUtil.URL_API + "/consultas/search";
    private static final String SEARCH_HORARIOS = ConstantsUtil.URL_API + "/consultas/searchHorarios";
    private static final String UPDATE = ConstantsUtil.URL_API + "/consultas/update";
    private static final String REAGENDAR = ConstantsUtil.URL_API + "/consultas/copy";
    private static final String CREATE = ConstantsUtil.URL_API + "/consultas/create";
    private static final String CONSULTA = ConstantsUtil.URL_API + "/consultas/";

    public static List<Consulta> search(List<String> agendasId) {
        URLUtil urlUtil = new URLUtil(SEARCH).addParam(agendasId.stream().collect(Collectors.joining(",")), "agendas");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "consultas"), new TypeReference<List<Consulta>>() {
        });
    }

    public static List<Consulta> search(int statusId, int consultorioId, int especialistaId, Date inicio, Date fim) {
        URLUtil urlUtil = new URLUtil(SEARCH)
                .addParam(consultorioId, "consultorios")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(inicio), "inicio")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(fim), "fim")
                .addParam(statusId, "status")
                .addParam(especialistaId, "especialista");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "consultas"), new TypeReference<List<Consulta>>() {
        });
    }

    public static List<SearchDTO> searchHorarios(int consultorioId, int especialistaId, Date inicio, Date fim) {
        URLUtil urlUtil = new URLUtil(SEARCH_HORARIOS)
                .addParam(consultorioId, "consultorio")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(inicio), "inicio")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(fim), "fim")
                .addParam(especialistaId, "especialista");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "search"), new TypeReference<List<SearchDTO>>() {
        });
    }

    public static List<SearchDTO> searchHorarios2(int consultorioId, Date inicio, Date fim) {
        URLUtil urlUtil = new URLUtil(SEARCH_HORARIOS)
                .addParam(consultorioId, "consultorio")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(inicio), "inicio")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(fim), "fim");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "search"), new TypeReference<List<SearchDTO>>() {
        });
    }

    public static Consulta searchHash(String hash) {
        URLUtil urlUtil = new URLUtil(SEARCH)
                .addParam(hash, "hash");

        List<Consulta> listaRetorno = JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "consultas"), new TypeReference<List<Consulta>>() {
        });

        return listaRetorno.get(0);
    }

    public static List<Consulta> searchHorariosToday(int consultorioId) {
        Date date = new Date();

        List<Consulta> listaVazia = new ArrayList<>();
        try {
            URLUtil urlUtil = new URLUtil(SEARCH)
                    .addParam(consultorioId, "consultorios")
                    .addParam(Functions.formateDateToStringFirstMinuteOfDay(date), "inicio")
                    .addParam(Functions.formateDateToStringLastMinuteOfDay(date), "fim");
            List<Consulta> listaRetorno = JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "consultas"), new TypeReference<List<Consulta>>() {
            });
            return listaRetorno;

        } catch (Exception e) {
            return listaVazia;
        }
    }

    public static List<Consulta> searchConsultasByStatus(int consultorioId, Date inicio, Date fim, List<Integer> statusIds) {
        URLUtil urlUtil = new URLUtil(SEARCH)
                .addParam(consultorioId, "consultorios")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(inicio), "inicio")
                .addParam(Functions.formateDateToStringLastMinuteOfDay(fim), "fim")
                .addParam(statusIds.stream().map(String::valueOf).collect(Collectors.joining(",")), "status");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "consultas"), new TypeReference<List<Consulta>>() {
        });
    }

    public static List<Consulta> searchConsultasByStatusAndEspecialista(int consultorioId, int especialistaId, Date inicio, Date fim, List<Integer> statusIds) {
        URLUtil urlUtil = urlUtilWithDates(SEARCH, inicio, fim)
                .addParam(consultorioId, "consultorios")
                .addParam(statusIds.stream().map(String::valueOf).collect(Collectors.joining(",")), "status")
                .addParam(especialistaId, "especialista");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "consultas"), new TypeReference<List<Consulta>>() {
        });
    }

    private static URLUtil urlUtilWithDates(String url, Date inicio, Date fim) {
        return new URLUtil(url).addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(inicio), "inicio")
                .addParam(Functions.formateDateToStringLastMinuteOfDay(fim), "fim");
    }

    public static Consulta getConsulta(String idConsulta) {
        URLUtil urlUtil = new URLUtil(CONSULTA + idConsulta);
        JSONObject objConsultaJSON = new JSONObject(JSONUtil.getEntityFromAPI(urlUtil.getUrl(), String.class)).getJSONObject("consulta");
        return JSONUtil.extractEntityFromJSON(objConsultaJSON.toString(), new TypeReference<Consulta>() {
        });

    }

    public static void update(Consulta consulta) {JSONUtil.sendEntityReturningEntityUpdated(consulta, UPDATE);}

    public static void reagendar(Consulta consulta) {
        JSONUtil.sendEntityReturningEntityUpdated(consulta, REAGENDAR);
    }

    public static void create(ConsultaDTO consultaDTO) {
        JSONUtil.sendEntityReturningStatusCode(consultaDTO, CREATE);
    }
}
