package br.com.devdojo.projetoinicial.service.restricao;

import br.com.devdojo.projetoinicial.persistence.model.Restricao;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * Created by André Ribeiro, William Suane on 3/31/17.
 */
public class RestricaoService {
    private static final String SEARCH = ConstantsUtil.URL_API + "/restricao";

    public static List<Restricao> getAllRestricoes() {
        URLUtil urlUtil = new URLUtil(SEARCH);
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "restricoes"), new TypeReference<List<Restricao>>() {
        });
    }
}
