package br.com.devdojo.projetoinicial.service.especialistaespecialidade;

import br.com.devdojo.projetoinicial.persistence.model.Especialidade;
import br.com.devdojo.projetoinicial.persistence.model.EspecialistaEspecialidade;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Andre Ribeiro on 3/30/2017.
 */
public class EspecialistaEspecialidadeService {
    private static final String SEARCH = ConstantsUtil.URL_API + "/especialistaespecialidades/search";
    private static final String CREATE = ConstantsUtil.URL_API + "/especialistaespecialidades";
    private static final String DELETE = ConstantsUtil.URL_API + "/especialistaespecialidades";
    private static final String UPDATE = ConstantsUtil.URL_API + "/especialistaespecialidades";

    public static List<EspecialistaEspecialidade> getAllEspecialistaEspecialidadeByConsultorio(List<String> consultorios) {
        URLUtil urlUtil = new URLUtil(SEARCH).addParam(consultorios.stream().collect(Collectors.joining(",")),"consultorios");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "especialistaEspecialidade"),
                new TypeReference<List<EspecialistaEspecialidade>>() {
        });
    }
    public static List<Especialidade> getAllEspecialistaEspecialidadeByEspecialista(int especialista) {
        URLUtil urlUtil = new URLUtil(SEARCH).addParam(especialista, "especialista");
        List<EspecialistaEspecialidade> listaEspecialistaEspecialidade =
                JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(),
                        "especialistaEspecialidade"), new TypeReference<List<EspecialistaEspecialidade>>() {
        });
        List<Especialidade> especialidadesEspecialista = listaEspecialistaEspecialidade.stream().
                map(EspecialistaEspecialidade::getEspecialidade).collect(Collectors.toList());
        return especialidadesEspecialista;
    }

    public static List<EspecialistaEspecialidade> getEspecialistaEspecialidadeByEspecialista(int especialista) {
        URLUtil urlUtil = new URLUtil(SEARCH).addParam(especialista, "especialista");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(),
                        "especialistaEspecialidade"), new TypeReference<List<EspecialistaEspecialidade>>() {
        });
    }

    public static void deleteEspecialistaEspecialidade(EspecialistaEspecialidade especialistaEspecialidade){
        JSONUtil.deleteEntityReturningStatusCode(especialistaEspecialidade, DELETE);
    }

    public static void updateEspecialistaEspecialidade(List<EspecialistaEspecialidade> especialistaEspecialidadeList){
        JSONUtil.sendListOfEntityReturningStatusCode(especialistaEspecialidadeList, UPDATE);
    }

    public static List<EspecialistaEspecialidade> create(List<EspecialistaEspecialidade> especialistaEspecialidades) {
        return JSONUtil.sendEntityReturningCustomListOfObjects(especialistaEspecialidades, CREATE, "especialistaEspecialidade",
                new TypeReference<List<EspecialistaEspecialidade>>() {
        });
    }

}
