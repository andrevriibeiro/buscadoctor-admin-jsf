package br.com.devdojo.projetoinicial.service.fatura;

import br.com.devdojo.projetoinicial.persistence.model.Fatura;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author Andre Ribeiro on 2/22/2017.
 */
public class FaturaService {
    private static final String SEARCH = ConstantsUtil.URL_API + "/faturas/search";
    private static final String GET = ConstantsUtil.URL_API + "/faturas/";
    private static final String UPDATE_FATURA_DO_FATURAMENTO = ConstantsUtil.URL_API + "/faturas/updateFaturamento";
    private static final String UPDATE = ConstantsUtil.URL_API + "/faturas/update";

    public static List<Fatura> getAllFaturasByStatus(int consultorioId, int statusFinalizado) {
        URLUtil urlUtil = new URLUtil(SEARCH);
        urlUtil.addParam(consultorioId, "consultorio").addParam(statusFinalizado, "status");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "faturas"), new TypeReference<List<Fatura>>() {
        });
    }

    public static List<Fatura> getAllFaturasByStatusAndConvenio(int consultorioId, int statusFinalizado, int convenioId) {
        URLUtil urlUtil = new URLUtil(SEARCH);
        urlUtil.addParam(consultorioId, "consultorio").addParam(statusFinalizado, "status").addParam(convenioId, "convenio");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "faturas"), new TypeReference<List<Fatura>>() {
        });
    }

    public static Fatura getFaturaById(int faturaId) {
        return JSONUtil.getEntityFromAPI(GET + faturaId, Fatura.class);

    }

    public static List<Fatura> getFaturaByFaturamentoId(int faturamentoId) {
        URLUtil urlUtil = new URLUtil(SEARCH);
        urlUtil.addParam(faturamentoId, "faturamento");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "faturas"), new TypeReference<List<Fatura>>() {
        });

    }

    public static int updateFaturaDoFaturamento(List<Fatura> fatura) {
        return JSONUtil.sendListOfEntityReturningStatusCode(fatura, UPDATE_FATURA_DO_FATURAMENTO);
    }

    public static void update(Fatura fatura) {
        JSONUtil.sendEntityReturningEntityUpdated(fatura, UPDATE);
    }

}
