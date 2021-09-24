package br.com.devdojo.projetoinicial.service.faturaprocedimento;

import br.com.devdojo.projetoinicial.persistence.model.FaturaProcedimento;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.Functions;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public class FaturaProcedimentoService {
    private static final String SEARCH = ConstantsUtil.URL_API + "/faturaProcedimentos/search";
    private static final String UPDATE = ConstantsUtil.URL_API + "/faturaProcedimentos/batchUpdate";
    private static final String DELETE = ConstantsUtil.URL_API + "/faturaProcedimentos/batchDelete";
    private static final String CREATE = ConstantsUtil.URL_API + "/faturaProcedimentos";

    public static List<FaturaProcedimento> getFaturaProcedimento(int faturaId) {
        URLUtil urlUtil = new URLUtil(SEARCH).addParam(faturaId, "fatura");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "faturaProcedimentos"), new TypeReference<List<FaturaProcedimento>>() {
        });
    }

    public static List<FaturaProcedimento> getFaturasContasPagar(int consultorioId, int statusFechado, List<String> conveniosListId, int especialistaId, Date inicio, Date fim) {
        URLUtil urlUtil = new URLUtil(SEARCH);
        buildPartialURL(consultorioId, conveniosListId, inicio, fim, urlUtil)
                .addParam(statusFechado, "status")
                .addParam(especialistaId, "especialista");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "faturaProcedimentos"), new TypeReference<List<FaturaProcedimento>>() {
        });
    }
    public static List<FaturaProcedimento> getFaturaProcedimentoByConsulta(int consultaId) {
        URLUtil urlUtil = new URLUtil(SEARCH).addParam(consultaId, "consulta");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "faturaProcedimentos"), new TypeReference<List<FaturaProcedimento>>() {
        });
    }

    public static int update(List<FaturaProcedimento> faturaProcedimento) {
        return JSONUtil.sendListOfEntityReturningStatusCode(faturaProcedimento, UPDATE);
    }
    public static FaturaProcedimento save(FaturaProcedimento faturaProcedimento) {
        return JSONUtil.sendEntityReturningExtractedObjectFromJSON(faturaProcedimento, CREATE,"faturaProcedimento", new TypeReference<FaturaProcedimento>() {
        });
    }
    public static int delete(List<FaturaProcedimento> faturaProcedimento) {
        return JSONUtil.deleteListOfEntityReturningStatusCode(faturaProcedimento, DELETE);
    }

    private static URLUtil buildPartialURL(int consultorioId, List<String> conveniosListId, Date inicio, Date fim, URLUtil urlUtil) {
        return urlUtil.addParam(conveniosListId.stream().collect(Collectors.joining(",")), "convenios")
                .addParam(consultorioId, "consultorio")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(inicio), "inicio")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(fim), "fim");
    }
}
