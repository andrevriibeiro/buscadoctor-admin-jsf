package br.com.devdojo.projetoinicial.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author Andre Ribeiro on 3/22/2017.
 */
public class FacesUtils {
    public static void addErrorMessage(String key, boolean keepMessages){
        addMessage(FacesMessage.SEVERITY_ERROR, getBundleValue(key),keepMessages);
    }
    public static void addWarningMessage(String key, boolean keepMessages){
        addMessage(FacesMessage.SEVERITY_WARN, getBundleValue(key),keepMessages);
    }
    public static void addErrorMessageNoBundle(String message, boolean keepMessages){
        addMessage(FacesMessage.SEVERITY_ERROR, message,keepMessages);
    }

    public static void addSuccessMessage(String key, boolean keepMessages){
        addMessage(FacesMessage.SEVERITY_INFO, getBundleValue(key),keepMessages);
    }
    private static void addMessage(FacesMessage.Severity severity, String msg, boolean keepMessages) {
        final FacesMessage facesMessage = new FacesMessage(severity, msg, "");
        if(keepMessages)
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setRedirect(true);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    private static String getBundleValue(String key) {
        return FacesContext.getCurrentInstance()
                .getApplication()
                .getResourceBundle(FacesContext.getCurrentInstance(), "m")
                .getString(key);
    }
}
