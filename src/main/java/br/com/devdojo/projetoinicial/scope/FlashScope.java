package br.com.devdojo.projetoinicial.scope;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 * @author Andre Ribeiro on 3/22/2017.
 */
public class FlashScope {

    public static Object getScopeAsObject(String key) {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return flash.get(key);
    }

    public static void setScope(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(key, value);
    }


}
