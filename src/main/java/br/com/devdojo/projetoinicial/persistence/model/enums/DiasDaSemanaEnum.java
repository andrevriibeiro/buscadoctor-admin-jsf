package br.com.devdojo.projetoinicial.persistence.model.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andre Ribeiro on 4/6/2017.
 */
public enum DiasDaSemanaEnum {
    SEGUNDA("Segunda", 2, "MONDAY"),
    TERCA("Terça", 3, "TUESDAY"),
    QUARTA("Quarta", 4, "WEDNESDAY"),
    QUINTA("Quinta", 5, "THURSDAY"),
    SEXTA("Sexta", 6, "FRIDAY"),
    SABADO("Sábado", 7, "SATURDAY"),
    DOMINGO("Domingo", 1, "SUNDAY");

    private String label;
    private int dia;
    private String labelEnglish;
    private static Map<Integer, DiasDaSemanaEnum> map = new HashMap<>();


    DiasDaSemanaEnum(String label, int dia, String labelEnglish) {
        this.label = label;
        this.labelEnglish = labelEnglish;
        this.dia = dia;
    }

    static {
        for (DiasDaSemanaEnum diaDaSemana : DiasDaSemanaEnum.values()) {
            map.put(diaDaSemana.getDia(), diaDaSemana);
        }
    }

    public String getLabelEnglish() {
        return labelEnglish;
    }

    public static DiasDaSemanaEnum getEnumById(Integer id) {
        return map.get(id);
    }

    public String getLabel() {
        return label;
    }

    public int getDia() {
        return dia;
    }
}
