package br.com.devdojo.projetoinicial.service.permissao;

import br.com.devdojo.projetoinicial.persistence.model.Permissao;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import java.util.List;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public class PermissaoService {
    private static final String PERMISSOES = "/permissoes/";

    public static List<Permissao> getAllPermissoes() {
        URLUtil urlUtil = new URLUtil(ConstantsUtil.URL_API + PERMISSOES);
        JSONArray arrayPacientesJSON = new JSONObject(JSONUtil.getEntityFromAPI(urlUtil.getUrl(), String.class)).getJSONArray("permissoes");

        return JSONUtil.extractEntityFromJSON(arrayPacientesJSON.toString(), new TypeReference<List<Permissao>>() {
        });
    }
}
