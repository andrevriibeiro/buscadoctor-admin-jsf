package br.com.devdojo.projetoinicial.service.procedimentoespecialista;

import br.com.devdojo.projetoinicial.persistence.model.ProcedimentoEspecialista;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author Andre Ribeiro on 3/23/17.
 */
public class ProcedimentoEspecialistaService {
    private static final String SEARCH = ConstantsUtil.URL_API + "/procedimentoEspecialistas/search";
    private static final String CREATE = ConstantsUtil.URL_API + "/procedimentoEspecialistas";
    private static final String BATCH_UPDATE = ConstantsUtil.URL_API + "/procedimentoEspecialistas/batchUpdate";

    public static List<ProcedimentoEspecialista> search(int especialistaId, int convenioId, int consultorioId, boolean liberado) {
        URLUtil urlUtil =  basicSearchFieldsForProcedimentoEspecialista(especialistaId, convenioId, consultorioId).addParam(liberado, "liberado");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "procedimentoEspecialistas"), new TypeReference<List<ProcedimentoEspecialista>>() {
        });
    }
    public static List<ProcedimentoEspecialista> searchByEspecialistaConvenioAndProcedimento(int especialistaId, int convenioId, int consultorioId, int procedimentoId) {
        URLUtil urlUtil =  basicSearchFieldsForProcedimentoEspecialista(especialistaId, convenioId, consultorioId).addParam(procedimentoId,"procedimento");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "procedimentoEspecialistas"), new TypeReference<List<ProcedimentoEspecialista>>() {
        });
    }

    private static URLUtil basicSearchFieldsForProcedimentoEspecialista(int especialistaId, int convenioId, int consultorioId) {
        return new URLUtil(SEARCH).addParam(especialistaId, "especialista")
                .addParam(convenioId, "convenio")
                .addParam(consultorioId, "consultorio");
    }

    public static int update(List<ProcedimentoEspecialista> procedimentoEspecialistaList) {
        return procedimentoEspecialistaList.isEmpty() ? HttpStatus.OK.value() : JSONUtil.sendListOfEntityReturningStatusCode(procedimentoEspecialistaList, BATCH_UPDATE);
    }
    public static ProcedimentoEspecialista create(ProcedimentoEspecialista procedimentoEspecialista) {
        return JSONUtil.sendEntityReturningExtractedObjectFromJSON(procedimentoEspecialista, CREATE, "procedimentoEspecialista", new TypeReference<ProcedimentoEspecialista>() {});
    }

}
