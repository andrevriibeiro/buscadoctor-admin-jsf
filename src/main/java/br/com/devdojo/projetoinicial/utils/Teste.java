package br.com.devdojo.projetoinicial.utils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public class Teste {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }
}
