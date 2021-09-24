package br.com.devdojo.projetoinicial.listener;

import br.com.devdojo.projetoinicial.bean.app.ApplicationBean;
import br.com.devdojo.projetoinicial.bean.login.LoginBean;

import javax.inject.Inject;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author André Ribeiro, William Suane on 4/25/17.
 */
@WebListener
public class ReleaseAgendaHorarioFromMemoryListener implements HttpSessionListener {
    private final LoginBean loginBean;
    private final ApplicationBean applicationBean;

    @Inject
    public ReleaseAgendaHorarioFromMemoryListener(LoginBean loginBean, ApplicationBean applicationBean) {
        this.loginBean = loginBean;
        this.applicationBean = applicationBean;
    }


    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        //Libera as agendasHorários que por um acaso não foram removidas do appBean referente a essa sessão
        applicationBean.getAgendaHorariosInUse().removeAll(loginBean.getAgendaHorariosInUseSession());
    }
}
