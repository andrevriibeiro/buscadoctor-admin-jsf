package br.com.devdojo.projetoinicial.persistence.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public enum StatusFaturaEnum {
    ABERTO(1), FECHADO(2), GLOSADO(3);
    private int status;

    StatusFaturaEnum(int status) {
        this.status = status;
    }

    private static Map<Integer, StatusFaturaEnum> map = new HashMap<>();

    static {
        for (StatusFaturaEnum status : StatusFaturaEnum.values()) {
            map.put(status.getStatus(), status);
        }
    }
    public static StatusFaturaEnum getEnumByStatusId(Integer statusId) {
        return map.get(statusId);
    }

    public int getStatus() {
        return status;
    }
}
