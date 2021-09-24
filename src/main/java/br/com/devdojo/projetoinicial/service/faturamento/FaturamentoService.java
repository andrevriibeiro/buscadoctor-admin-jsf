package br.com.devdojo.projetoinicial.service.faturamento;

import br.com.devdojo.projetoinicial.persistence.model.Faturamento;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusFaturamentoEnum;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.Functions;
import br.com.devdojo.projetoinicial.utils.JSONUtil;
import br.com.devdojo.projetoinicial.utils.URLUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.primefaces.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Andre Ribeiro on 2/21/2017.
 */
public class FaturamentoService {
    private static final String SEARCH = ConstantsUtil.URL_API + "/faturamento/search";
    private static final String SEARCH_FATURAMENTO_ABERTO = ConstantsUtil.URL_API + "/faturamento/searchFaturamentosAbertos";
    private static final String CREATE = ConstantsUtil.URL_API + "/faturamento";
    private static final String UPDATE = ConstantsUtil.URL_API + "/faturamento/update";
    private static final String BATCH_UPDATE = ConstantsUtil.URL_API + "/faturamento/batchUpdate";
    private static final String DELETE = ConstantsUtil.URL_API + "/faturamento";
    private static final String GET = ConstantsUtil.URL_API + "/faturamento/";

    public static List<Faturamento> getAllFaturamentoByStatus(int consultorioId, int statusFinalizado) {
        URLUtil urlUtil = new URLUtil(SEARCH);
        urlUtil.addParam(consultorioId, "consultorio").addParam(statusFinalizado, "finalizado");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "faturamentos"), new TypeReference<List<Faturamento>>() {
        });
    }

    public static Faturamento getById(int faturamentoId) {
        JSONObject jsonObject = new JSONObject(JSONUtil.getEntityFromAPI(GET + faturamentoId, String.class));
        return JSONUtil.extractEntityFromJSON(jsonObject.get("faturamento").toString(), new TypeReference<Faturamento>() {
        });
    }

    public static List<Faturamento> search(int consultorioId, List<String> conveniosListId, int statusFinalizado, Date inicio, Date fim) {
        URLUtil urlUtil = new URLUtil(SEARCH);
        buildPartialURL(consultorioId, conveniosListId, inicio, urlUtil)
                .addParam(statusFinalizado, "finalizado")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(fim), "fim");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "faturamentos"), new TypeReference<List<Faturamento>>() {
        });
    }

    /**
     * Busca os faturamentos abertos baseados no consultório, convênio e na data de início
     * @param consultorioId Id do consultório
     * @param conveniosListId Convênios
     * @param inicio Data inicial da consulta
     * @return Lista de faturamentos abertos com a data inicial maior do que a data início da consulta
     */
    public static List<Faturamento> search(int consultorioId, List<String> conveniosListId, Date inicio) {
        URLUtil urlUtil = new URLUtil(SEARCH_FATURAMENTO_ABERTO);
        buildPartialURL(consultorioId, conveniosListId, inicio, urlUtil);
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "faturamentos"), new TypeReference<List<Faturamento>>() {
        });
    }

    private static URLUtil buildPartialURL(int consultorioId, List<String> conveniosListId, Date inicio, URLUtil urlUtil) {
        return urlUtil.addParam(conveniosListId.stream().collect(Collectors.joining(",")), "convenios")
                .addParam(consultorioId, "consultorio")
                .addParam(Functions.formateDateToStringYYYYMMDDHHMMSS(inicio), "inicio");
    }


    /***
     * Procura por faturamentos que possuem Fatura
     * @param faturamentoIds Lista de ids dos faturamentos
     * @return Lista de Faturamento que possuem faturas associadas a ele
     */
    public static Set<Faturamento> search(List<Integer> faturamentoIds) {
        URLUtil urlUtil = new URLUtil(SEARCH);
        urlUtil.addParam(faturamentoIds.stream().map(String::valueOf).collect(Collectors.joining(",")), "faturamentos")
                .addParam(StatusFaturamentoEnum.ABERTO.getStatus(), "finalizado");
        return JSONUtil.extractEntityFromJSON(JSONUtil.extractJSONArrayFromJSONResponse(urlUtil.getUrl(), "faturamentos"), new TypeReference<Set<Faturamento>>() {
        });
    }

    public static int create(List<Faturamento> faturamento) {
        return JSONUtil.sendListOfEntityReturningStatusCode(faturamento, CREATE);
    }

    public static Faturamento update(Faturamento faturamento) {
        return JSONUtil.sendEntityReturningEntityUpdated(faturamento, UPDATE);
    }

    public static int update(List<Faturamento> faturamento) {
        return JSONUtil.sendListOfEntityReturningStatusCode(faturamento, BATCH_UPDATE);
    }


    public static int deleteFauramento(Faturamento faturamento) {
        return JSONUtil.deleteEntityReturningStatusCode(faturamento, DELETE);
    }

}
