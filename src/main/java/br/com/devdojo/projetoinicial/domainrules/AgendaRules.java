package br.com.devdojo.projetoinicial.domainrules;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;
import br.com.devdojo.projetoinicial.persistence.model.*;
import br.com.devdojo.projetoinicial.persistence.model.enums.AgendaTipoEnum;
import br.com.devdojo.projetoinicial.persistence.model.enums.DiasDaSemanaEnum;
import br.com.devdojo.projetoinicial.service.especialistaconsultorio.EspecialistaConsultorioService;
import br.com.devdojo.projetoinicial.utils.FacesUtils;
import br.com.devdojo.projetoinicial.utils.Functions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

import static br.com.devdojo.projetoinicial.utils.Functions.dateToLocalDateTime;
import static br.com.devdojo.projetoinicial.utils.Functions.localDateTimeToDate;

/**
 * @author André Ribeiro, William Suane on 3/31/17.
 */
public class AgendaRules {
    public static List<String> gerarDiasRestritos(List<Restricao> restricaoList, int especialistaId, LoginBean loginBean) {
        EspecialistaConsultorio especialistaConsultorio = EspecialistaConsultorioService.search(especialistaId, loginBean.getPerfil().getConsultorio().getId());
        List<String> datasRestritas = new ArrayList<>();
        if (!especialistaConsultorio.getTrabalhaferiado()) {
            for (Restricao restricao : restricaoList) {
                LocalDate start = restricao.getDataInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate end = restricao.getDataFinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Stream.iterate(start, date -> date.plusDays(1))
                        .limit(ChronoUnit.DAYS.between(start, end) + 1)
                        .forEach(date -> datasRestritas.add("'" + date.format(DateTimeFormatter.ofPattern("yyyy-M-d")) + "'"));
            }
        }
        return datasRestritas;
    }

    public static void validateDate(List<Agenda> agendaList, String message, String pattern, Locale locale) {
        agendaList.sort(Comparator.comparing(Agenda::getDataInicio));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, locale);
        for (int i = 0; i < agendaList.size(); i++) {
            LocalDateTime inicio = dateToLocalDateTime(agendaList.get(i).getDataInicio());
            LocalDateTime fim = dateToLocalDateTime(agendaList.get(i).getDataFinal());
            for (int j = i + 1; j < agendaList.size(); j++) {
                LocalDateTime nextInicio = dateToLocalDateTime(agendaList.get(j).getDataInicio());
                LocalDateTime nextFim = dateToLocalDateTime(agendaList.get(j).getDataFinal());
                if ((inicio.isBefore(nextFim) || inicio.isEqual(nextFim)) && (fim.isAfter(nextInicio) || fim.isEqual(nextInicio))) {
                    StringBuilder sb = new StringBuilder(30);
                    sb.append(dateTimeFormatter.format(nextInicio)).append(" ~ ").append(dateTimeFormatter.format(nextFim));
                    FacesUtils.addErrorMessageNoBundle(message + " - " + sb.toString(), false);
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    /***
     * O TimePicker do primefaces coloca a hora mas deixa a data com 1 de janeiro de 1970,
     * este método adiciona o horário do TimePicker a data chave do map
     * @param dateAgendasMap Map com a data e a lista de agendas
     * @return Lista de agendas com data e horário formatadas
     */
    public static List<Agenda> addDateToTime(Map<Date, List<Agenda>> dateAgendasMap) {
        List<Agenda> agendaList = new ArrayList<>();
        dateAgendasMap.keySet()
                .forEach(date -> dateAgendasMap.get(date).forEach(agenda -> {
                    agenda.setDataInicio(localDateTimeToDate(addTimeToDate(date, agenda.getDataInicio())));
                    agenda.setDataFinal(localDateTimeToDate(addTimeToDate(date, agenda.getDataFinal())));
                    agendaList.add(agenda);
                }));
        return agendaList;
    }

    /***
     *
     * @param day Data com dia mes e ano que você vai adicionar o parâmetro time
     * @param time Hora minuto e segundo que você vai adicionar ao dia
     * @return LocalDateTime com um merge entre esse data e hora
     */
    public static LocalDateTime addTimeToDate(Date day, Date time) {
        return LocalDateTime.of(Functions.dateToLocalDate(day), Functions.dateToLocalTime(time));
    }


    public static Agenda createAgendaBasicFields(Date date, AgendaTipoEnum agendaTipoEnum, LoginBean loginBean, int especialistaId) {
        return Agenda.newBuilder()
                .diaSemana(AgendaRules.addTimeToDate(date, date).getDayOfWeek().getValue())
                .tipoAgenda(TipoAgenda.newBuilder().id(agendaTipoEnum.getId()).build())
                .consultorio(loginBean.getPerfil().getConsultorio())
                .liberado(true)
                .visivelApp(true)
                .especialista(new Especialista(especialistaId))
                .build();
    }

    public static Agenda createAgendaBasicFields(Date inicio, Date fim, AgendaTipoEnum agendaTipoEnum, LoginBean loginBean, int especialistaId, int tempoConsulta) {
        Agenda agendaBasicFields = createAgendaBasicFields(inicio, agendaTipoEnum, loginBean, especialistaId);
        agendaBasicFields.setDataInicio(inicio);
        agendaBasicFields.setDataFinal(fim);
        agendaBasicFields.setTempoConsulta(tempoConsulta);
        return agendaBasicFields;
    }

    public static Agenda createAgendaBasicFields(Date date, AgendaTipoEnum agendaTipoEnum, LoginBean loginBean, int especialistaId, DiasDaSemanaEnum diasDaSemanaEnum) {
        Agenda agendaBasicFields = createAgendaBasicFields(date, agendaTipoEnum, loginBean, especialistaId);
        agendaBasicFields.setDiaSemana(diasDaSemanaEnum.getDia());
        return agendaBasicFields;
    }
    public static int calculateQuantidadeConsultas(Agenda agenda){
        int qtdeConsulta = 0;
        LocalDateTime inicio = Functions.dateToLocalDateTime(agenda.getDataInicio());
        do{
           qtdeConsulta++;
        }while((inicio = inicio.plusMinutes(agenda.getTempoConsulta())).isBefore(Functions.dateToLocalDateTime(agenda.getDataFinal())));
        return qtdeConsulta;
    }

}
