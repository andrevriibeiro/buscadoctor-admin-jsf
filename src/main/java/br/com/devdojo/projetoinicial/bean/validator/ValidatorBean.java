package br.com.devdojo.projetoinicial.bean.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
@Named
public class ValidatorBean {
    public void validateEmailRegex(FacesContext context,
                                   UIComponent componentToValidate,
                                   Object value) throws ValidatorException {
        if (value.toString().isEmpty()) return;
        if (!value.toString().matches("[\\w\\.-]*[a-zA-Z0-9_]@[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]")) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please enter a valid email", "");
            throw new ValidatorException(message);
        }
    }

    public void validateQuantidadeProcedimento(FacesContext context,
                                               UIComponent componentToValidate,
                                               Object value) {
        if (value.toString().isEmpty()) return;
        if(Integer.valueOf(value.toString()) < 1){
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "A quantidade não pode ser menor que 1", "");
            throw new ValidatorException(message);
        }

    }
}
