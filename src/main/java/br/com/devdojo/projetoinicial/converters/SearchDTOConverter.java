package br.com.devdojo.projetoinicial.converters;

import br.com.devdojo.projetoinicial.persistence.model.dto.SearchDTO;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Map;

/**
 * @author Andre Ribeiro on 3/22/2017.
 */
@FacesConverter(value = "searchDTOConverter")
public class SearchDTOConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value == null) {
            return null;
        }
        return this.getAttributesFrom(uiComponent).get(value);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value != null && !value.equals("")) {
            SearchDTO searchDTO = (SearchDTO) value;
            if (searchDTO.getDiaInicio() != null) {
                this.addAttribute(uiComponent, searchDTO);
                return searchDTO.getDiaInicio().toString();
            }
            return value.toString();
        }
        return "";
    }

    private void addAttribute(UIComponent component, SearchDTO searchDTO) {
        this.getAttributesFrom(component).put(searchDTO.getDiaInicio().toString(), searchDTO);
    }

    private Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

}
