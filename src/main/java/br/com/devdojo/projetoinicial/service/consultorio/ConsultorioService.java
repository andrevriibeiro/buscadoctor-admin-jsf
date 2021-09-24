package br.com.devdojo.projetoinicial.service.consultorio;

import br.com.devdojo.projetoinicial.persistence.model.Consultorio;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.primefaces.json.JSONObject;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public class ConsultorioService {
    private static final String CONSULTORIO = ConstantsUtil.URL_API + "/consultorios/";
    private static final String UPDATE = ConstantsUtil.URL_API + "/consultorios/update";

    public static Consultorio getById(int consultorio) {
        URLUtil urlUtil = new URLUtil(CONSULTORIO + consultorio);
        JSONObject objConsultorio = new JSONObject(JSONUtil.getEntityFromAPI(urlUtil.getUrl(), String.class)).getJSONObject("consultorio");
        return JSONUtil.extractEntityFromJSON(objConsultorio.toString(), new TypeReference<Consultorio>() {
        });
    }

    public static void update(Consultorio consultorio) {
        JSONUtil.sendEntityReturningEntityUpdated(consultorio, UPDATE);
    }
}
