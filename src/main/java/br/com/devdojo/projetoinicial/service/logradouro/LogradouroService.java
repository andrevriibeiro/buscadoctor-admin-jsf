package br.com.devdojo.projetoinicial.service.logradouro;

import br.com.devdojo.projetoinicial.persistence.model.Cidade;
import br.com.devdojo.projetoinicial.persistence.model.Estado;
import br.com.devdojo.projetoinicial.persistence.model.Logradouro;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public class LogradouroService {
    private static final String CEP_URL = "http://cep.republicavirtual.com.br/web_cep.php?formato=json";
    private static final String SEARCH_SAVED_CEP_URL = ConstantsUtil.URL_API+"/logradouros/search";
    private static final String CREATE = ConstantsUtil.URL_API+"/logradouros/";

    /**
     * Esse metodo eh responsavel por encontrar o logradouro em nosso banco de dados
     *
     * @param cep
     * @return logradouro
     *
     * @implNote caso não for encontrado em nosso banco de dados nenhum logradouro com o CEP informado,
     * então esse metodo irá chamar o metodo @getLogradouroOnExternalServer que irá fazer a busca em um server externo
    * */
    public static Logradouro searchLogradouroOnDatabase(String cep) {
        URLUtil urlUtil = new URLUtil(SEARCH_SAVED_CEP_URL);
        urlUtil.addParam(cep, "cep");
        JSONArray logradouros = new JSONObject(JSONUtil.getEntityFromAPI(urlUtil.getUrl(), String.class)).getJSONArray("logradouros");

        if (logradouros.length() > 0) {
            return JSONUtil.extractEntityFromJSON(logradouros.get(0).toString(), new TypeReference<Logradouro>() {
            });
        } else {
            return getLogradouroOnExternalServer(cep);
        }
    }

    /**
    * Este metodo ira procurar em um servidor externo o logradouro pertecente ao CEP informado no parametro
    * Esse metodo só sera chamado caso o logradouro nao estiver cadastradado em nosso banco de dados.
    * */
    private static Logradouro getLogradouroOnExternalServer(String cep) {
        String cepFormat = null;
        if (cep.contains("-")) { cepFormat = cep.replace("-", "");}

        URLUtil urlUtil = new URLUtil(CEP_URL);
        urlUtil.addParam(cepFormat, "cep");
        JSONObject jsonObject = new JSONObject(JSONUtil.getEntityFromAPI(urlUtil.getUrl(), String.class));

        Logradouro logradouro = new Logradouro();
        logradouro.setCep(cep);
        logradouro.setTipo(jsonObject.getString("tipo_logradouro"));
        logradouro.setNome(jsonObject.getString("logradouro"));
        logradouro.setBairro(jsonObject.getString("bairro"));
        Cidade cidade = new Cidade();
        cidade.setNome(jsonObject.getString("cidade"));
        Estado estado = new Estado();
        estado.setAcronimo(jsonObject.getString("uf").toUpperCase());
        cidade.setEstado(estado);
        logradouro.setCidade(cidade);

        return logradouro;
    }


    public static Logradouro createLogradouro(Logradouro logradouro){
        return JSONUtil.sendEntityReturningEntityUpdated(logradouro,CREATE);
    }
}
