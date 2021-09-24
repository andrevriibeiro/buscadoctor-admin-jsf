package br.com.devdojo.projetoinicial.domainrules;

import br.com.devdojo.projetoinicial.persistence.model.Convenio;
import br.com.devdojo.projetoinicial.persistence.model.Faturamento;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusFaturamentoEnum;
import br.com.devdojo.projetoinicial.service.faturamento.FaturamentoService;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Andre Ribeiro on 2/23/2017.
 */
public class FaturamentoRules {

    public static Set<Convenio> buscarConveniosPeriodoFaturamento(Date inicio, Date fim, int consultorioId, Set<Convenio> convenios) {
        if (inicio != null && fim != null) {
            List<String> conveniosIdList = convenios.stream().map(convenio -> convenio.getId().toString()).collect(Collectors.toList());
            List<Faturamento> faturamentos = FaturamentoService.search(consultorioId, conveniosIdList, StatusFaturamentoEnum.ABERTO.getStatus(), inicio, fim);
            return faturamentos.stream().map(Faturamento::getConvenio).collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
//    public static boolean validateDataVencimentoFaturamento(Faturamento faturamento) {
//        if (!faturamento.getDataVencimento().equals(faturamento.getFim()) && !faturamento.getDataVencimento().after(faturamento.getFim())) {
//            FacesUtils.addErrorMessage("errorDataVencimentoInvalida", false);
//            return true;
//        }
//        return false;
//    }

    public static void closeFaturamento(Faturamento faturamento) {
        faturamento.setFinalizado(true);
        FaturamentoService.update(faturamento);
    }
    public static void closeFaturamento(List<Faturamento> faturamento) {
        faturamento.forEach(f->f.setFinalizado(true));
        FaturamentoService.update(faturamento);
    }
}
