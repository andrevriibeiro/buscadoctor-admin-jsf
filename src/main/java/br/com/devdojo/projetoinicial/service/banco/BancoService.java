package br.com.devdojo.projetoinicial.service.banco;

import br.com.devdojo.projetoinicial.persistence.model.Banco;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import java.util.List;

/**
 * @author Andre Ribeiro on 2/22/2017.
 */
public class BancoService {
    private static final String ALL = ConstantsUtil.URL_API + "/bancos/";

    public static List<Banco> getAllBancos() {
        JSONArray convenios = new JSONObject(JSONUtil.getEntityFromAPI(ALL, String.class)).getJSONArray("bancos");
        return JSONUtil.extractEntityFromJSON(convenios.toString(), new TypeReference<List<Banco>>() {});
    }


}