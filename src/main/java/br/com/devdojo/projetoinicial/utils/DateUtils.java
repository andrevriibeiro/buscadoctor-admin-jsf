package br.com.devdojo.projetoinicial.utils;

import java.time.format.DateTimeFormatter;

import static br.com.devdojo.projetoinicial.utils.ConstantsUtil.*;

/**
 * @author Andre Ribeiro
 */
public class DateUtils {
    public static DateTimeFormatter dateTimeFormatterDBStyleNoTime = DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD);
    public static DateTimeFormatter dateTimeFormatterDBStyleWithTime = DateTimeFormatter.ofPattern(PATTERN_YYYY_MM_DD_HH_MM_SS);
    public static DateTimeFormatter dateTimeFormatterPresentationNoTime = DateTimeFormatter.ofPattern(PATTERN_DD_MM_YYYY);
}