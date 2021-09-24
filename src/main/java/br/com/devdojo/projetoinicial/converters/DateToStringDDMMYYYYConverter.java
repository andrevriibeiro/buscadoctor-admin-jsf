package br.com.devdojo.projetoinicial.converters;

import br.com.devdojo.projetoinicial.utils.Functions;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Date;

/**
 * @author Andre Ribeiro on 3/22/2017.
 */
@FacesConverter(value = "dateToStringDDMMYYYYConverter")
public class DateToStringDDMMYYYYConverter implements Converter {
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        String date = (modelValue != null) ? Functions.formateDateToStringDDMMYYYY((Date) modelValue) : null;
        return (date != null) ? date : "";
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }


        return Functions.formateDateStringDDMMYYYToDate(submittedValue);

    }

}
