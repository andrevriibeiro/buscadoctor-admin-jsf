package br.com.devdojo.projetoinicial.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * @author Andre Ribeiro
 * @version 1.1.2
 * @since 1.1.2
 */
public class URLUtil {

    private final StringBuilder url = new StringBuilder(80);
    private static final Logger logger = LoggerFactory.getLogger(URLUtil.class);

    public URLUtil(String url) {
        this.url.append(url);
    }

    public URLUtil addParam(Object paramValue, String paramName) {
        if (paramValue != null && !paramValue.toString().isEmpty()) {
            if (((paramValue instanceof Collection) && ((List) paramValue).isEmpty()))
                return this;

//            String value = paramValue.toString().replace(" ","+");
            url.append(url.indexOf("=") < 0 ? "?" : "&").append(paramName).append("=").append(paramValue);

        }
        return this;
    }

    public String getUrl() {
        return url.toString();
    }
}