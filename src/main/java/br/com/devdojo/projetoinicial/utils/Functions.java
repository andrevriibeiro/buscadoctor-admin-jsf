package br.com.devdojo.projetoinicial.utils;

import br.com.devdojo.projetoinicial.persistence.model.Consulta;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusConsultaEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static br.com.devdojo.projetoinicial.utils.DateUtils.*;

/**
 * Classe de funcoes gerais
 *
 * @author Guilherme Mendes
 * @version 1.1.2
 * @since 1.0.0
 */
public class Functions {

    private static final Logger LOGGER = LoggerFactory.getLogger(Functions.class);

    /**
     * Recebe uma data String no formato String yyyy-MM-dd HH:mm:ss e formata
     * para Date
     *
     * @param dateString data String no formato String yyyy-MM-dd HH:mm:ss
     * @return Um objeto Date do @param
     */
    public static Date formateDateTime(String dateString) {

        LocalDateTime parse = LocalDateTime.parse(dateString, dateTimeFormatterDBStyleWithTime);
        Date.from(parse.atZone(ZoneId.systemDefault()).toInstant());

        return Date.from(parse.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Recebe uma data String no formato String dd/mm/yyyy e formata
     * para LocalDate
     *
     * @param dateString data String no formato String dd/mm/yyyy
     * @return Um objeto Date do @param
     */
    public static LocalDate dateStringDDMMYYYToLocalDate(String dateString) {

        return LocalDate.parse(dateString, dateTimeFormatterPresentationNoTime);
    }

    /**
     * Recebe um date e retorna no formato yyyy-MM-dd HH:mm:ss
     *
     * @param date a ser formatado
     * @return Uma String contendo o Date formatado no padrao yyyy-MM-dd HH:mm:ss
     */
    public static String formateDateToStringYYYYMMDDHHMMSS(Date date) {
        if (date == null) return null;
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(dateTimeFormatterDBStyleWithTime);
    }

    /**
     * Recebe um date e retorna no formato yyyy-MM-dd 23:59:59
     *
     * @param date a ser formatado
     * @return Uma String contendo o Date formatado no padrao yyyy-MM-dd 23:59:59
     */
    public static String formateDateToStringLastMinuteOfDay(Date date) {
        if (date == null) return null;
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).with(LocalTime.MAX);
        return localDateTime.format(dateTimeFormatterDBStyleWithTime);
    }

    /**
     * Recebe um date e retorna no formato yyyy-MM-dd 00:00:00
     *
     * @param date a ser formatado
     * @return Uma String contendo o Date formatado no padrao yyyy-MM-dd 00:00:00
     */
    public static String formateDateToStringFirstMinuteOfDay(Date date) {
        if (date == null) return null;
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).with(LocalTime.MIN);
        return localDateTime.format(dateTimeFormatterDBStyleWithTime);
    }

    /***
     * Recebe uma data e coloca o dia dela pro primeiro dia do mês
     * @param date Data que vai ser colocado o dia como o primeiro dia do mês
     * @return data com o dia primeiro
     */
    public static Date dateToFirstDayOfMonth(Date date) {
        LocalDateTime firstDayOfMonth = dateToLocalDateTime(date).with(TemporalAdjusters.firstDayOfMonth());
        return localDateTimeToDate(firstDayOfMonth);
    }
    /***
     * Recebe uma data e coloca o dia dela pro último dia do mês
     * @param date Data que vai ser colocado o dia como o último dia do mês
     * @return data com o último dia
     */
    public static Date dateToLastDayOfMonth(Date date) {
        LocalDateTime lastDayOfMonth = dateToLocalDateTime(date).with(TemporalAdjusters.lastDayOfMonth());
        return localDateTimeToDate(lastDayOfMonth);
    }

    /**
     * Recebe um date e retorna um LocalDate
     *
     * @param date para ser Transformado em LocalDate
     * @return LocalDate
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalTime dateToLocalTime(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Recebe um LocalDate e retorna um Date
     *
     * @param localDate para ser Transformado em Date
     * @return LocalDate
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /***
     * Will set the time of that date to 00:00:00
     * @param date any date
     * @return date with time 00:00:00
     */
    public static Date setTimeDateToFirstMinuteOfDay(Date date) {
        if (date == null) return null;
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).with(LocalTime.MIN);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /***
     * Will set the time of that date to 23:59:59
     * @param date any date
     * @return date with time 23:59:59
     */
    public static Date setTimeDateToMaxMinuteOfDay(Date date) {
        if (date == null) return null;
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).with(LocalTime.MAX);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * Recebe um date e retorna no formato dd/MM/yyyy
     *
     * @param date a ser formatado
     * @return Uma String contendo o Date formatado no padrao dd/MM/yyyy
     */
    public static String formateDateToStringDDMMYYYY(Date date) {
        if (date == null) return null;
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(dateTimeFormatterPresentationNoTime);
    }

    /**
     * Recebe uma String no formato yyyy-mm-dd e retorna um Date
     *
     * @param dateYYYYMMDD String no formato yyyy-mm-dd
     * @return Um Date
     */
    public static Date formateDateStringYYYYMMDDToDate(String dateYYYYMMDD) {
        LocalDate parse = LocalDate.parse(dateYYYYMMDD, dateTimeFormatterDBStyleNoTime);
        return Date.from(parse.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Recebe um date e retorna uma String no formato yyyy-MM-dd
     *
     * @param date date
     * @return String no formato yyyy-MM-dd
     */
    public static String formateDateToStringYYYYMMDD(Date date) {
        if (date == null) return null;
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(dateTimeFormatterDBStyleNoTime);
    }

    /**
     * Recebe uma String no formato dd/MM/yyyy e retorna um Date
     *
     * @param dateDDMMYYYY String no formato dd/MM/yyyy
     * @return Um Date
     */
    public static Date formateDateStringDDMMYYYToDate(String dateDDMMYYYY) {
        if (dateDDMMYYYY == null || dateDDMMYYYY.isEmpty()) return null;
        LocalDate parse = LocalDate.parse(dateDDMMYYYY, dateTimeFormatterPresentationNoTime);
        return Date.from(parse.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static void clearCollection(Collection<?> collection) {
        if (collection != null)
            collection.clear();
    }


    /**
     * Esse metodo é responsavel por retornar uma lista de idStatus das consultas
     *
     * @return lista de idStatus das consultas
    * */
    public static List<Integer> getStatusConsultas() {
        ArrayList<Integer> status = new ArrayList<>();

        status.add(StatusConsultaEnum.APROVADA.getId());
        status.add(StatusConsultaEnum.CANCELADA.getId());
        status.add(StatusConsultaEnum.AGUARDANDO_ATENDIMENTO.getId());
        status.add(StatusConsultaEnum.EM_ATENDIMENTO.getId());
        status.add(StatusConsultaEnum.ATENDIDO.getId());
        status.add(StatusConsultaEnum.DISPONIVEL.getId());
        status.add(StatusConsultaEnum.CONFIRMADO.getId());
        status.add(StatusConsultaEnum.PENDENTE_APROVAÇÃO.getId());
        status.add(StatusConsultaEnum.NAO_COMPARECEU.getId());
        status.add(StatusConsultaEnum.PENDENTE_CANCELAMENTO.getId());
        status.add(StatusConsultaEnum.PENDENTE_CONFIRMACAO.getId());
        status.add(StatusConsultaEnum.REJEITADA.getId());
        status.add(StatusConsultaEnum.REMARCADO.getId());

        return status;
    }

    /**
     * Esse metodo eh responsavel por retornar a quantidade de consultas
     * que possui o status informado
     *
     * @param consultas, status
     * @return quantidade de consultas que possui o status enviado por parametro.
     * */
    public static int searchQtdConsultasByStatus(List<Consulta> consultas, Integer status) {

        return (int) consultas.stream().
                filter(consulta -> consulta.getStatus().getId().equals(status))
                .count();
    }

    /**
     * Esse metodo eh responsavel por converter um numero que representa um mes
     * para um mês em string
     *
     * @param mes representado por um numero
     * @return mes representado por uma string
     *
     * */
    public static String convertIntToStringDate(int mes){
        return new DateFormatSymbols().getMonths()[mes-1];
    }


    /**
     * Esse metodo eh responsavel por retirar toda a mascaras de um numero de telefone
     *
     *
     * */
    public static String getMeMyNumber(String number) {
        String out = number
                .replaceAll("[^0-9\\+]", "") // remove all the non numbers (brackets dashes spaces etc.) except the + signs
                .replaceAll("(.)(\\++)(.)", "$1$3") // if there are left out +'s in the middle by mistake, remove them
                .replaceAll("(^0{2}|^\\+)(.+)", "$2"); // make 00XXX... numbers and +XXXXX.. numbers into XXXX...

        return out;
    }
}