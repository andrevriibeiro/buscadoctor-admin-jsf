package br.com.devdojo.projetoinicial.service.procedimentoconvenio;

import br.com.devdojo.projetoinicial.persistence.model.ProcedimentoConvenio;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author André Ribeiro, William Suane on 5/3/17.
 */
public class ProcedimentoConvenioService {
    private static final String SEARCH_PROCEDIMENTO_CONVENIO_PERMITIDOS_CONSULTA = ConstantsUtil.URL_API + "/procedimentoConvenios/searchProcedimentoConvenioPermitidoConsulta";
    private static final String SEARCH_PROCEDIMENTO_BY_CONVENIO = ConstantsUtil.URL_API + "/procedimentoConvenios/search";

    public static List<ProcedimentoConvenio> search(int especialistaId, int consultorioId) {
        URLUtil urlUtil = new URLUtil(SEARCH_PROCEDIMENTO_CONVENIO_PERMITIDOS_CONSULTA).addParam(especialistaId, "especialista").addParam(consultorioId, "consultorio");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "procedimentoConvenios"), new TypeReference<List<ProcedimentoConvenio>>() {
        });
    }

    public static List<ProcedimentoConvenio> search(int especialistaId, int consultorioId, int convenioId) {
        URLUtil urlUtil = new URLUtil(SEARCH_PROCEDIMENTO_BY_CONVENIO).
                addParam(especialistaId, "especialista")
                .addParam(consultorioId, "consultorio")
                .addParam(convenioId, "convenio");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "procedimentoConvenios"), new TypeReference<List<ProcedimentoConvenio>>() {
        });
    }
}
