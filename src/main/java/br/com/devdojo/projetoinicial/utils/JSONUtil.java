package br.com.devdojo.projetoinicial.utils;

import br.com.devdojo.projetoinicial.persistence.model.AbstractEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public class JSONUtil {
    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    public static <T> T getEntityFromAPI(String url, Class<T> clazz) {
        return new RestTemplate().getForEntity(url, clazz).getBody();
    }

    public static <T> T extractEntityFromJSON(String json, TypeReference<T> clazz) {
        return parseObjectFromStringJSON(json, clazz);

    }

    public static <T extends AbstractEntity> T sendEntityReturningEntityUpdated(T entity, String url) {
        ResponseEntity<T> exchange = new RestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(entity, getHeadersJSON()), new ParameterizedTypeReference<T>() {
        });

        return exchange.getBody();
    }

    public static <T extends AbstractEntity> T sendEntityReturningExtractedObjectFromJSON(T entity, String url, String keyToExtractFromJSON, TypeReference<T> clazz) {
        ResponseEntity<String> exchange = getStringResponseEntity(entity, url);
        return parseObjectFromStringJSON(new JSONObject(exchange.getBody()).get(keyToExtractFromJSON).toString(), clazz);

    }

    public static <T, R extends AbstractEntity> R sendEntityReturningCustomNonListObject(T entity, String url, String keyToExtractFromJSON, TypeReference<R> clazz) {
        ResponseEntity<String> exchange = getStringResponseEntity(entity, url);
        return parseObjectFromStringJSON(new JSONObject(exchange.getBody()).get(keyToExtractFromJSON).toString(), clazz);
    }

    public static <T, R extends List<? extends AbstractEntity>> R sendEntityReturningCustomListOfObjects(T entity, String url, String keyToExtractFromJSON, TypeReference<R> clazz) {
        ResponseEntity<String> exchange = getStringResponseEntity(entity, url);
        return extractEntityFromJSON(new JSONObject(exchange.getBody()).get(keyToExtractFromJSON).toString(), clazz);
    }

    public static <T, R extends List<? extends AbstractEntity>> R sendEntityReturningObjectFromBody(T entity, String url, TypeReference<R> clazz) {
        ResponseEntity<String> exchange = getStringResponseEntity(entity, url);
        return extractEntityFromJSON(exchange.getBody(), clazz);
    }


    public static <T extends AbstractEntity> int sendListOfEntityReturningStatusCode(List<T> entity, String url) {
        ResponseEntity<T> exchange = getResponseEntity(entity, url);
        return exchange.getStatusCode().value();
    }

    public static <T extends AbstractEntity> int sendEntityReturningStatusCode(T entity, String url) {
        ResponseEntity<T> exchange = getResponseEntity(entity, url);
        return exchange.getStatusCode().value();
    }

    public static <T extends AbstractEntity> int deleteEntityReturningStatusCode(T entity, String url) {
        ResponseEntity<T> exchange = new RestTemplate().exchange(url, HttpMethod.DELETE, new HttpEntity<>(entity, getHeadersJSON()), new ParameterizedTypeReference<T>() {
        });

        return exchange.getStatusCode().value();
    }

    public static <T extends AbstractEntity> int deleteListOfEntityReturningStatusCode(List<T> entity, String url) {
        ResponseEntity<T> exchange = new RestTemplate().exchange(url, HttpMethod.DELETE, new HttpEntity<>(entity, getHeadersJSON()), new ParameterizedTypeReference<T>() {
        });

        return exchange.getStatusCode().value();
    }

    public static String extractJSONArrayFromJSONResponse(String url, String key) {
        String entity = JSONUtil.getEntityFromAPI(url, String.class);
        JSONObject objectResponse = new JSONObject(entity);
        return objectResponse.getJSONArray(key).toString();
    }

    private static <T> T parseObjectFromStringJSON(String json, TypeReference<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("Erro ao tentar realizar o parse do JSON utilizando o wrapper do jackson ", e);
            return null;
        }
    }

    private static <T> ResponseEntity<String> getStringResponseEntity(T entity, String url) {
        return new RestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(entity, getHeadersJSON()), String.class);
    }

    private static <T extends AbstractEntity> ResponseEntity<T> getResponseEntity(List<T> entity, String url) {
        return new RestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(entity,
                getHeadersJSON()), new ParameterizedTypeReference<T>() {
        });
    }

    private static <T extends AbstractEntity> ResponseEntity<T> getResponseEntity(T entity, String url) {
        return new RestTemplate().exchange(url, HttpMethod.POST, new HttpEntity<>(entity,
                getHeadersJSON()), new ParameterizedTypeReference<T>() {
        });
    }

    private static HttpHeaders getHeadersJSON() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
