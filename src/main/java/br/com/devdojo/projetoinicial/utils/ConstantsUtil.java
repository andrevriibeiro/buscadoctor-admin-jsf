package br.com.devdojo.projetoinicial.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public class ConstantsUtil {
    public static String URL_API;
    public static final String PATTERN_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DD_MM_YYYY = "dd/MM/yyyy";
    public static final String PATTERN_HH_MM = "HH:mm";
    public static final String PATTERN_EEEE_MMMM_HH_MM = "EEEE, dd 'de' MMMM 'as' HH:mm";
    private static final String IP_PRODUCAO = "50.116.36.218";
    private static boolean ipProducaoUsadoEmDev;
    private static final Logger logger = LoggerFactory.getLogger(ConstantsUtil.class);

    static {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream()))) {
            String ip = in.readLine();
            if (ip.contains(IP_PRODUCAO)) createProductionConnectionURL();
            else createDevConnectionURL();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("URL API: " + URL_API);
    }

    private static void createProductionConnectionURL() {
        URL_API = String.format("http://%s:8085/api-dev", IP_PRODUCAO);
    }

    private static void createDevConnectionURL() {
        //URL_API = "http://localhost:8085/api-dev";

        //URL_API = "http://appointbr.com:8085/api-dev";
        URL_API = "http://devdojo.ddns.net:8085/api-dev";
        ipProducaoUsadoEmDev = URL_API.contains(IP_PRODUCAO);
    }

    public static boolean isIpProducaoUsadoEmDev() {
        return ipProducaoUsadoEmDev;
    }
}