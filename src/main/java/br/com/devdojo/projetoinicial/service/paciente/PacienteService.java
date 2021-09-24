package br.com.devdojo.projetoinicial.service.paciente;

import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.Paciente;
import br.com.devdojo.projetoinicial.persistence.model.dto.PacienteDTO;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.Functions;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public class PacienteService {
    private static final String PACIENTE = ConstantsUtil.URL_API + "/pacientes/";
    private static final String SEARCH = ConstantsUtil.URL_API + "/pacientes/search";
    private static final String SEARCH_CONSULTA = ConstantsUtil.URL_API + "/consultas/search";
    private static final String CREATE = ConstantsUtil.URL_API + "/pacientes/create";
    private static final String UPDATE = ConstantsUtil.URL_API + "/pacientes/update";

    public static List<Paciente> searchPacientes(Paciente paciente) {
        URLUtil urlUtil = new URLUtil(SEARCH);
        urlUtil.addParam(paciente.getConsultorio().getId(), "consultorio")
                .addParam(paciente.getUsuario().getNome(), "nome")
                .addParam(paciente.getUsuario().getCpf(), "cpf")
                .addParam(paciente.getUsuario().getCelular(), "celular")
                .addParam(paciente.getUsuario().getTelefoneResidencia(), "telefoneresidencia")
                .addParam(Functions.formateDateToStringYYYYMMDD(paciente.getUsuario().getBirthday()), "birthday");
        JSONArray arrayPacientesJSON = new JSONObject(JSONUtil.getEntityFromAPI(urlUtil.getUrl(), String.class)).getJSONArray("pacientes");

        return JSONUtil.extractEntityFromJSON(arrayPacientesJSON.toString(), new TypeReference<List<Paciente>>() {
        });
    }

    public static Paciente getPacienteById(int idPaciente) {
        URLUtil urlUtil = new URLUtil(PACIENTE + idPaciente);
        JSONObject objPacienteJSON = new JSONObject(JSONUtil.getEntityFromAPI(urlUtil.getUrl(), String.class)).getJSONObject("paciente");
        return JSONUtil.extractEntityFromJSON(objPacienteJSON.toString(), new TypeReference<Paciente>() {
        });
    }

    public static List<Consulta> getConsultasPacienteByConsultorio(int consultorioId, int usuarioId, List<Integer> statusIds) {
        URLUtil urlUtil = new URLUtil(SEARCH_CONSULTA);
        urlUtil.addParam(statusIds.stream().map(String::valueOf).collect(Collectors.joining(",")), "status").
                addParam(consultorioId, "consultorios").addParam(usuarioId, "usuario");

        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "consultas"),
                new TypeReference<List<Consulta>>() {
                });
    }

    public static Paciente create(PacienteDTO pacienteDTO) {
        return JSONUtil.sendEntityReturningCustomNonListObject(pacienteDTO, CREATE, "paciente", new TypeReference<Paciente>() {

        });
    }

    public static Paciente update(PacienteDTO pacienteDTO) {
        return JSONUtil.sendEntityReturningCustomNonListObject(pacienteDTO, UPDATE, "paciente", new TypeReference<Paciente>() {

        });
    }
}
