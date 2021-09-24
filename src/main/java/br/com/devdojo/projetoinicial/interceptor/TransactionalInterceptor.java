package br.com.devdojo.projetoinicial.interceptor;

import br.com.devdojo.projetoinicial.annotations.Transactional;
import br.com.devdojo.projetoinicial.utils.FacesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.Iterator;

/**
 * @author Andre Ribeiro on 3/22/2017.
 */
@Interceptor
@Transactional
public class TransactionalInterceptor implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionalInterceptor.class);

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        Object result;
        boolean keepMessages;
        try {
            result = context.proceed();
            keepMessages = !context.getMethod().getReturnType().equals(Void.TYPE);
        } catch (Exception e) {
            Iterator<FacesMessage> messages = FacesContext.getCurrentInstance().getMessages();
            if (!messages.hasNext()) {
                LOGGER.error("Erro: ", e);
                FacesUtils.addErrorMessageNoBundle("Ocorreu um erro ao efetuar a operação, contate o administrador do sistema", false);
            }
            return null;
        }
        Iterator<FacesMessage> messages = FacesContext.getCurrentInstance().getMessages();
        if (!messages.hasNext())
            FacesUtils.addSuccessMessage("sucessoOperacao", keepMessages);
        return result;
    }
}
