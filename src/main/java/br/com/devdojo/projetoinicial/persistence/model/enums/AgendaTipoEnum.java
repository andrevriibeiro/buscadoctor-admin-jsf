package br.com.devdojo.projetoinicial.persistence.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André Ribeiro, William Suane on 3/30/17.
 */
public enum AgendaTipoEnum {
    DIARIO(1, "Diário", "HH:mm", "Hora Início", "Hora fim"),
    QUINZENAL_MENSAL(2, "Quinzenal Mensal", "dd/MM/yyyy HH:mm","Período Início","Período Fim"),
    QUINZENAL_ANUAL(3, "Quinzenal Anual", "dd/MM/yyyy HH:mm","Período Início","Período Fim"),
    MENSAL(4, "Mensal", "dd/MM/yyyy HH:mm","Período Início","Período Fim"),
    ANUAL(5, "Anual", "dd/MM/yyyy HH:mm","Período Início","Período Fim");
    private int id;
    private String displayName;
    private String pattern;
    private String labelInicio;
    private String labelFim;
    private static Map<Integer, AgendaTipoEnum> map = new HashMap<>();

    AgendaTipoEnum(int id, String displayName, String pattern, String labelInicio, String labelFim) {
        this.id = id;
        this.displayName = displayName;
        this.pattern = pattern;
        this.labelInicio = labelInicio;
        this.labelFim = labelFim;
    }

    static {
        for (AgendaTipoEnum agenda : AgendaTipoEnum.values()) {
            map.put(agenda.getId(), agenda);
        }
    }

    public static AgendaTipoEnum getEnumById(Integer id) {
        return map.get(id);
    }

    public String getLabelInicio() {
        return labelInicio;
    }

    public String getLabelFim() {
        return labelFim;
    }

    public String getPattern() {
        return pattern;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }
}
