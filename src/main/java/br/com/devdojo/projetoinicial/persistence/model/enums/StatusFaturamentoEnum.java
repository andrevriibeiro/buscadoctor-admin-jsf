package br.com.devdojo.projetoinicial.persistence.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andre Ribeiro on 2/21/2017.
 */
public enum StatusFaturamentoEnum {
    ABERTO(0), FECHADO(1);
    private int status;
    private static Map<Integer, StatusFaturamentoEnum> map = new HashMap<>();

    static {
        for (StatusFaturamentoEnum status : StatusFaturamentoEnum.values()) {
            map.put(status.getStatus(), status);
        }
    }

    public static StatusFaturamentoEnum getEnumByStatusId(Integer statusId) {
        return map.get(statusId);
    }

    StatusFaturamentoEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
