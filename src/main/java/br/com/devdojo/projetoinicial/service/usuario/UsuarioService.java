package br.com.devdojo.projetoinicial.service.usuario;

import br.com.devdojo.projetoinicial.persistence.model.Usuario;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.Functions;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import java.util.List;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public class UsuarioService {
    private static final String SEARCH = "/usuarios/search";
    private static final String USUARIO = ConstantsUtil.URL_API + "/usuarios";
    private static final String UPDATE = ConstantsUtil.URL_API + "/usuarios/update";

    public static List<Usuario> searchUsuarios(Usuario usuario) {
        URLUtil urlUtil = new URLUtil(ConstantsUtil.URL_API + SEARCH);
        urlUtil.addParam(usuario.getNome(), "nome")
                .addParam(usuario.getCpf(), "cpf")
                .addParam(usuario.getCelular(), "celular")
                .addParam(usuario.getTelefoneResidencia(), "telefoneresidencia")
                .addParam(Functions.formateDateToStringYYYYMMDD(usuario.getBirthday()), "birthday");
        JSONArray arrayPacientesJSON = new JSONObject(JSONUtil.getEntityFromAPI(urlUtil.getUrl(), String.class)).getJSONArray("usuarios");

        return JSONUtil.extractEntityFromJSON(arrayPacientesJSON.toString(), new TypeReference<List<Usuario>>() {
        });
    }

    public static void create(Usuario usuario) {
        Usuario usuarioRetorno = new Usuario();
        JSONUtil.sendEntityReturningEntityUpdated(usuario, USUARIO);

        //return usuarioRetorno;
    }

    public static void updatePaciente(Usuario usuario) {
        JSONUtil.sendEntityReturningEntityUpdated(usuario, UPDATE);
    }
}
