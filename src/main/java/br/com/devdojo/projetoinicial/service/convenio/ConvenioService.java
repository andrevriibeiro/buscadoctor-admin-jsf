package br.com.devdojo.projetoinicial.service.convenio;

import br.com.devdojo.projetoinicial.persistence.model.Convenio;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import java.util.List;

/**
 * @author Andre Ribeiro on 2/22/2017.
 */
public class ConvenioService {
    private static final String ALL = ConstantsUtil.URL_API + "/convenios/";

    public static List<Convenio> getAllConvenios() {
       JSONArray convenios = new JSONObject(JSONUtil.getEntityFromAPI(ALL, String.class)).getJSONArray("convenios");
        return JSONUtil.extractEntityFromJSON(convenios.toString(), new TypeReference<List<Convenio>>() {});
    }
}