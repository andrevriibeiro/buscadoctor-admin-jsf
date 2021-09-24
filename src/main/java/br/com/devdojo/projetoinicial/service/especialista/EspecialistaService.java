package br.com.devdojo.projetoinicial.service.especialista;

import br.com.devdojo.projetoinicial.persistence.model.Especialista;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaEspecialidade;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author Andre Ribeiro on 3/30/2017.
 */
public class EspecialistaService {
    private static final String SEARCH = ConstantsUtil.URL_API + "/especialistas/search";
    private static final String ESPECIALISTAS = ConstantsUtil.URL_API + "/especialistas/";
    private static final String UPDATE = ConstantsUtil.URL_API + "/especialistas/update";

    public static Especialista create(Especialista especialista) {
        Especialista especRetorno;
        especRetorno = JSONUtil.sendEntityReturningEntityUpdated(especialista, ESPECIALISTAS);
        return especRetorno;
    }

    public static List<Especialista> findByCrm(String crm) {
        URLUtil urlUtil = new URLUtil(SEARCH);
        urlUtil.addParam(crm, "crm");
        return JSONUtil.extractEntityFromJSON(JSONUtil
                        .extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "especialistas"), new TypeReference<List<Especialista>>() {
        });
    }

    public static Especialista getEspecialistaEspecialidadeById(int id) {
        URLUtil urlUtil = new URLUtil(ESPECIALISTAS + id);
        List<EspecialistaEspecialidade> e = JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(),
                "especialistaEspecialidade"), new TypeReference<List<EspecialistaEspecialidade>>() {
        });

        return e.get(0).getEspecialista();
    }

    public static void update(Especialista especialista) {
        JSONUtil.sendEntityReturningEntityUpdated(especialista, UPDATE);
    }
}
