package br.com.devdojo.projetoinicial.loginfilter;

import br.com.devdojo.projetoinicial.bean.login.LoginBean;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author André Ribeiro, William Suane on 3/16/17.
 */
public class LoginFilter implements Filter {
    @Inject
    private LoginBean loginBean;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse res = (HttpServletResponse) response;
//        HttpServletRequest req = (HttpServletRequest) request;
//        if (loginBean.isLogged()) {
//            chain.doFilter(request, response);
//        } else {
//            res.sendRedirect(req.getServletContext().getContextPath() + "/login");
//        }
//    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestDispatcher dispatcher;
        String uri = ((HttpServletRequest) request).getRequestURI();



        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

//        System.out.println(uri);
//        System.out.println(loginBean.getPaginaPermissaoMap().keySet().contains(uri));
//        logger.warn("IP: {}", request.getRemoteAddr());


        if (!loginBean.isLogged()) {
            res.sendRedirect(req.getServletContext().getContextPath() + "/login.xhtml");
        } else if (!verifyUrl(uri, req.getContextPath().length()) && !loginBean.isAdmin()) {
            res.sendRedirect(req.getServletContext().getContextPath() + "/access.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean verifyUrl(String uri, int contextPathLength) {
        return loginBean.getPaginaPermissaoMap().keySet().contains(uri.substring(contextPathLength));
    }

    @Override
    public void destroy() {

    }

}
