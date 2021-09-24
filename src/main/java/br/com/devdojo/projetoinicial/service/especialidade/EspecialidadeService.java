package br.com.devdojo.projetoinicial.service.especialidade;

import br.com.devdojo.projetoinicial.persistence.model.Especialidade;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import java.util.List;

/**
 * Created by TruckYourWay01 on 5/8/2017.
 */
public class EspecialidadeService {

    private static final String ALL = ConstantsUtil.URL_API + "/especialidades/";

    public static List<Especialidade> getAllEspecialidades() {
        JSONArray convenios = new JSONObject(JSONUtil.getEntityFromAPI(ALL, String.class)).getJSONArray("especialidades");
        return JSONUtil.extractEntityFromJSON(convenios.toString(), new TypeReference<List<Especialidade>>() {});
    }

}
