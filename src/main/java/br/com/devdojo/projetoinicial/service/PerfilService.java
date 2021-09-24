package br.com.devdojo.projetoinicial.service;

import br.com.devdojo.projetoinicial.persistence.model.Perfil;
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
public class PerfilService {
    private static final String LOGAR = ConstantsUtil.URL_API + "/perfils/doLogin";
    private static final String USUARIOS = ConstantsUtil.URL_API + "/perfils/search?consultorio=";
    private static final String PERFIL = ConstantsUtil.URL_API + "/perfils/";
    private static final String UPDATE = ConstantsUtil.URL_API + "/perfils/update";

    public static Perfil efetuarLogin(Perfil perfil) {
        return JSONUtil.sendEntityReturningExtractedObjectFromJSON(perfil, LOGAR, "perfil", new TypeReference<Perfil>() {});

    }

    public static List<Perfil> getUsuariosClinica(int idClinica) {
        URLUtil urlUtil = new URLUtil(USUARIOS + idClinica);
        JSONArray arrayPerfilsJSON = new JSONObject(JSONUtil.getEntityFromAPI(urlUtil.getUrl(), String.class)).getJSONArray("perfils");
        return JSONUtil.extractEntityFromJSON(arrayPerfilsJSON.toString(), new TypeReference<List<Perfil>>() {
        });
    }

    public static Perfil getPerfilById(int idPerfil) {
        URLUtil urlUtil = new URLUtil(PERFIL + idPerfil);
        JSONObject objPerfilsJSON = new JSONObject(JSONUtil.getEntityFromAPI(urlUtil.getUrl(), String.class)).getJSONObject("perfil");
        return JSONUtil.extractEntityFromJSON(objPerfilsJSON.toString(), new TypeReference<Perfil>() {
        });
    }

    public static void update(Perfil perfil) {
        JSONUtil.sendEntityReturningEntityUpdated(perfil, UPDATE);
    }


}
