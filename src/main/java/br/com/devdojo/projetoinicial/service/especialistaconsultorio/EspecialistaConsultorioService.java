package br.com.devdojo.projetoinicial.service.especialistaconsultorio;

import br.com.devdojo.projetoinicial.persistence.model.EspecialistaConsultorio;
import br.com.devdojo.projetoinicial.persistence.model.dto.EspecialistaDTO;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.primefaces.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author André Ribeiro, William Suane on 4/17/17.
 */
public class EspecialistaConsultorioService {
    private static final String SEARCH_BY_ESPECIALISTA_AND_CONSULTORIO = ConstantsUtil.URL_API + "/especialistaConsultorios/searchByEspecialistaAndConsultorio";
    private static final String SEARCH = ConstantsUtil.URL_API + "/especialistaConsultorios/search";
    private static final String ASSOCIATE = ConstantsUtil.URL_API + "/especialistaConsultorios";
    private static final String UPDATE = ConstantsUtil.URL_API + "/especialistaConsultorios/update";

    public static void associateEspecialista(EspecialistaConsultorio especialistaConsultorio) {
        JSONUtil.sendEntityReturningEntityUpdated(especialistaConsultorio, ASSOCIATE);
    }

    public static EspecialistaConsultorio search(int especialistaId, int consultorioId) {
        URLUtil urlUtil = new URLUtil(SEARCH_BY_ESPECIALISTA_AND_CONSULTORIO)
                .addParam(especialistaId, "especialista")
                .addParam(consultorioId, "consultorio");
        JSONObject especialistaConsultorioJson = new JSONObject(JSONUtil.getEntityFromAPI(urlUtil.getUrl(), String.class))
                .getJSONObject("especialistaConsultorio");
        return JSONUtil.extractEntityFromJSON(especialistaConsultorioJson.toString(), new TypeReference<EspecialistaConsultorio>(){});
    }

    public static List<EspecialistaConsultorio> search(List<String> consultoriosId) {
        URLUtil urlUtil = new URLUtil(SEARCH)
                .addParam(consultoriosId.stream().collect(Collectors.joining(",")),"consultorios");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "especialistaConsultorios"), new TypeReference<List<EspecialistaConsultorio>>() {
        });
    }

    public static void update(EspecialistaConsultorio especialistaConsultorio) {
        JSONUtil.sendEntityReturningEntityUpdated(especialistaConsultorio, UPDATE);
    }

    public static List<EspecialistaDTO> searchEspecialistaConsultoriosEnabled(List<String> consultoriosId, boolean habilitado) {
        URLUtil urlUtil = new URLUtil(SEARCH)
                .addParam(consultoriosId.stream().collect(Collectors.joining(",")), "consultorios")
                .addParam(habilitado, "habilitado");
        return JSONUtil
                .extractEntityFromJSON(JSONUtil
                .extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "especialistaConsultorios"), new TypeReference<List<EspecialistaDTO>>() {
        });
    }
}
