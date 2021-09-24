package br.com.devdojo.projetoinicial.service.convenioconsultorio;

import br.com.devdojo.projetoinicial.persistence.model.ConsultorioConvenio;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author Andre Ribeiro on 2/22/2017.
 */
public class ConvenioConsultorioService {
    private static final String SEARCH = ConstantsUtil.URL_API + "/consultorioConvenios/search";
    private static final String CREATE_UPDATE = ConstantsUtil.URL_API + "/consultorioConvenios";
    private static final String DELETE = ConstantsUtil.URL_API + "/consultorioConvenios";

    public static List<ConsultorioConvenio> getConveniosConsultorio(int id) {
        URLUtil urlUtil = new URLUtil(SEARCH).addParam(id, "consultorio");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "consultorioConvenios"),
                new TypeReference<List<ConsultorioConvenio>>() {
        });
    }

     public static void update(List<ConsultorioConvenio> consultorioConveniosAdicionados) {
        if(consultorioConveniosAdicionados.size() > 0) {
            JSONUtil.sendEntityReturningObjectFromBody(consultorioConveniosAdicionados, CREATE_UPDATE,
                    new TypeReference<List<ConsultorioConvenio>>() {
                    });
        }
    }

    public static void delete(List<ConsultorioConvenio> consultorioConveniosRemovidos) {
        if(consultorioConveniosRemovidos.size()> 0){
            JSONUtil.deleteListOfEntityReturningStatusCode(consultorioConveniosRemovidos, DELETE);
        }
    }
}