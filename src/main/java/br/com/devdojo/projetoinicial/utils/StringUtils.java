package br.com.devdojo.projetoinicial.utils;

/**
 * @author Andre Ribeiro on 3/22/2017.
 */
public class StringUtils {
    public static String likeLower(String param) {
        return "%" + param.toLowerCase() + "%";
    }
    public static String like(String param) {
        return "%" + param + "%";
    }
}
