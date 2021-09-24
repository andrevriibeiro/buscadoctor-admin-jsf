package br.com.devdojo.projetoinicial.service.especialistaconvenio;

import br.com.devdojo.projetoinicial.persistence.model.Convenio;
import br.com.devdojo.projetoinicial.persistence.model.dto.EspecialistaConvenioDTO;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author André Ribeiro, William Suane on 4/25/17.
 */
public class EspecialistaConvenioService {
    private static final String SEARCH_CONVENIOS_PERMITIDOS_AGENDAMENTO = ConstantsUtil.URL_API + "/especialistaconvenios/searchConveniosPermitidosAgendamento";
    private static final String CONVENIOS_ESPECIALISTA = ConstantsUtil.URL_API + "/especialistaconvenios";

    public static List<Convenio> search(int especialistaId, int consultorioId) {
        URLUtil urlUtil = new URLUtil(SEARCH_CONVENIOS_PERMITIDOS_AGENDAMENTO)
                .addParam(especialistaId, "especialista")
                .addParam(consultorioId, "consultorio");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "convenios"),
                new TypeReference<List<Convenio>>() {
        });
    }

    public static void updateConvenioEspecialista(EspecialistaConvenioDTO especialistaConvenioDTO) {
        JSONUtil.sendEntityReturningEntityUpdated(especialistaConvenioDTO, CONVENIOS_ESPECIALISTA);
    }

    public static void deleteConvenioEspecialista(EspecialistaConvenioDTO especialistaConvenioDTO) {
        JSONUtil.deleteEntityReturningStatusCode(especialistaConvenioDTO, CONVENIOS_ESPECIALISTA);
    }
}
