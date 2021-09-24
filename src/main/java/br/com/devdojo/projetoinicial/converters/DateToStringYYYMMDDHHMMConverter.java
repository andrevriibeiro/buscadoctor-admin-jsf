package br.com.devdojo.projetoinicial.converters;

import br.com.devdojo.projetoinicial.utils.Functions;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Date;

/**
 * @author Andre Ribeiro on 4/4/2017.
 */
@FacesConverter(value = "dateToStringYYYMMDDHHMMConverter")
public class DateToStringYYYMMDDHHMMConverter implements Converter {
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        String date = (modelValue != null) ? Functions.formateDateToStringYYYYMMDDHHMMSS((Date) modelValue) : null;
        return (date != null) ? date : "";
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }

        return Functions.formateDateTime(submittedValue);

    }
}
