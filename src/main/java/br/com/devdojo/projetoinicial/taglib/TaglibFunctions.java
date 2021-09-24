package br.com.devdojo.projetoinicial.taglib;


import org.apache.commons.lang3.text.WordUtils;
import org.omnifaces.util.Faces;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Andre Ribeiro on 4/26/2017.
 */
public final class TaglibFunctions {
    private static final TimeZone TIMEZONE_DEFAULT = TimeZone.getDefault();

    public static String capitalize(String str) {
        return WordUtils.capitalizeFully(str);
    }

    public static String formatTwoDates(Date date1, Date date2, String pattern) {
        if (date1 != null && date2 != null) {
            DateFormat formatter = new SimpleDateFormat(pattern, Faces.getLocale());
            formatter.setTimeZone(TIMEZONE_DEFAULT);
            return new StringBuilder().append(formatter.format(date1)).append(" - ").append(formatter.format(date2)).toString();
        }
        return null;
    }

}
