package br.com.devdojo.projetoinicial.service.usuario;

import br.com.devdojo.projetoinicial.persistence.model.UsuarioConvenio;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * Created by Andre Ribeiro on 04/05/17.
 */
public class UsuarioConvenioService {
    private static final String USUARIO_CONVENIOS = ConstantsUtil.URL_API + "/usuarioConvenios/";

    public static List<UsuarioConvenio> getUsuarioConvenios(int usuarioId) {
        URLUtil urlUtil = new URLUtil(USUARIO_CONVENIOS);
        urlUtil.addParam(usuarioId, "usuario");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "usuarioConvenios"),
                new TypeReference<List<UsuarioConvenio>>() {
                });
    }

    public static void removeUsuarioConvenio(UsuarioConvenio usuarioConvenios) {
        JSONUtil.deleteEntityReturningStatusCode(usuarioConvenios, USUARIO_CONVENIOS);
    }
}

