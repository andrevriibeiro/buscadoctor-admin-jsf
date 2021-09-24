package br.com.devdojo.projetoinicial.bean.login;

import br.com.devdojo.projetoinicial.persistence.model.*;
import br.com.devdojo.projetoinicial.persistence.model.dto.SearchDTO;
import br.com.devdojo.projetoinicial.persistence.model.enums.StatusConsultaEnum;
import br.com.devdojo.projetoinicial.service.PerfilService;
import br.com.devdojo.projetoinicial.service.consulta.ConsultaService;
import br.com.devdojo.projetoinicial.service.especialistaconsultorio.EspecialistaConsultorioService;
import br.com.devdojo.projetoinicial.service.especialistaespecialidade.EspecialistaEspecialidadeService;
import br.com.devdojo.projetoinicial.utils.ConstantsUtil;
import br.com.devdojo.projetoinicial.utils.FacesUtils;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static br.com.devdojo.projetoinicial.utils.Functions.localDateToDate;

/**
 * @author by André Ribeiro, William Suane on 3/16/17.
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginBean.class);
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private Perfil perfil = new Perfil();
    private Locale locale = new Locale("pt", "BR");
    private Map<Especialista, List<Especialidade>> especialistaEspecialidadeMap;
    private Map<Consultorio, List<EspecialistaEspecialidade>> consultorioEspecialistaEspecialidadeMap = new LinkedHashMap<>();
    private Map<String, List<String>> paginaPermissaoMap;
    private Set<SearchDTO> agendaHorariosInUseSession = new HashSet<>();
    private Map<Consultorio, List<EspecialistaConsultorio>> consultorioEspecialistaConsultorioMap = new LinkedHashMap<>(2);

    private boolean admin;
    private int qtdConsultasPendentesAprovacao;
    private boolean ipProducaoUsadoEmDev;
    private boolean logged;

    public String logar() throws InterruptedException, ExecutionException, TimeoutException {
        try {
            //throws exception if not found
            this.perfil = PerfilService.efetuarLogin(this.perfil);
            paginaPermissaoMap = this.perfil.getPermissao()
                    .stream().filter(permissao -> permissao.getPagina() != null)
                    .collect(Collectors.groupingBy(Permissao::getPagina,
                    Collectors.mapping(Permissao::getNome, Collectors.toList())));

            admin = perfil.getAlias().equalsIgnoreCase("Administrador");
            logged = true;

            loadEspecialistasEspecialidade();
            loadConsultorioEspecialistaEspecialidade();
            loadEspecialistaConsultorio();

            ipProducaoUsadoEmDev = ConstantsUtil.isIpProducaoUsadoEmDev();
            qtdConsultasPendentesAprovacao = getQuatidadeConsultasPendenteAprovacao();

            return "/pages/consulta/todas?faces-redirect=true";

        } catch (HttpClientErrorException e) {
            LOGGER.error("Usuário e/ou senha incorretos");
            FacesUtils.addErrorMessage("errorUserNotFound", false);
        } catch (ResourceAccessException e) {
            LOGGER.error("API Fora do AR");
            FacesUtils.addErrorMessageNoBundle("Não foi possível efetuar a conexão para logar", false);
        }
        return null;
    }

    // sendo usado em varios lugares
    private void loadEspecialistasEspecialidade() {
        List<EspecialistaEspecialidade> especialistaEspecialidades = EspecialistaEspecialidadeService
                .getAllEspecialistaEspecialidadeByConsultorio(Collections.singletonList(perfil.getConsultorio().getId().toString()));

        especialistaEspecialidadeMap = especialistaEspecialidades.stream()
                .collect(Collectors.groupingBy(EspecialistaEspecialidade::getEspecialista, Collectors.mapping(EspecialistaEspecialidade::getEspecialidade, Collectors.toList())));
    }

    // sendo usado em varios lugares tbm
    public void loadConsultorioEspecialistaEspecialidade() {
        Consultorio consultorio = createConsultorioFilial();
        consultorioEspecialistaEspecialidadeMap.put(perfil.getConsultorio(), EspecialistaEspecialidadeService
                .getAllEspecialistaEspecialidadeByConsultorio(Collections
                        .singletonList(perfil.getConsultorio().getId().toString())));

        consultorioEspecialistaEspecialidadeMap.put(consultorio, EspecialistaEspecialidadeService
                .getAllEspecialistaEspecialidadeByConsultorio(Collections
                        .singletonList(consultorio.getId().toString())));
    }

    // sendo usado em varios lugares tbm
    private void loadEspecialistaConsultorio() throws InterruptedException, ExecutionException, TimeoutException {
        Consultorio consultorio = createConsultorioFilial();

        consultorioEspecialistaConsultorioMap.put(perfil.getConsultorio(),
                CompletableFuture.supplyAsync(() -> EspecialistaConsultorioService.search(
                        Collections.singletonList(perfil.getConsultorio().getId().toString())), executor).get(10, TimeUnit.SECONDS));

        consultorioEspecialistaConsultorioMap.put(consultorio,
                CompletableFuture.supplyAsync(() -> EspecialistaConsultorioService.search(
                        Collections.singletonList(consultorio.getId().toString())), executor).get(10, TimeUnit.SECONDS));
    }

    private int getQuatidadeConsultasPendenteAprovacao() {
        Date today = localDateToDate(LocalDate.now());
        Date oneYearLater = localDateToDate(LocalDate.now().plusYears(1));
        return ConsultaService.searchConsultasByStatus(perfil.getConsultorio().getId(), today, oneYearLater,
                Collections.singletonList(StatusConsultaEnum.PENDENTE_APROVAÇÃO.getId())).size();
    }

    public void addConsultaPendente() {
        qtdConsultasPendentesAprovacao = qtdConsultasPendentesAprovacao + 1;
        RequestContext ajax = RequestContext.getCurrentInstance();
        ajax.update("formPendent");
    }

    private Consultorio createConsultorioFilial() {
        Consultorio consultorio;

        if (perfil.getConsultorio().getId().equals(1)) {
            consultorio = Consultorio.newBuilder().id(2).nome("Ouromed").build();
        } else {
            consultorio = Consultorio.newBuilder().id(1).nome("Ouroclínica").build();
        }

        return consultorio;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.perfil = new Perfil();
        return "/login.xhtml?faces-redirect=true";
    }

    public boolean isIpProducaoUsadoEmDev() {
        return ipProducaoUsadoEmDev;
    }

    public void setIpProducaoUsadoEmDev(boolean ipProducaoUsadoEmDev) {this.ipProducaoUsadoEmDev = ipProducaoUsadoEmDev;}

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Map<String, List<String>> getPaginaPermissaoMap() {
        return paginaPermissaoMap;
    }

    public void setPaginaPermissaoMap(Map<String, List<String>> paginaPermissaoMap) {this.paginaPermissaoMap = paginaPermissaoMap;}

    public Set<SearchDTO> getAgendaHorariosInUseSession() {
        return agendaHorariosInUseSession;
    }

    public Map<Consultorio, List<EspecialistaEspecialidade>> getConsultorioEspecialistaEspecialidadeMap() {return consultorioEspecialistaEspecialidadeMap;}

    public void setConsultorioEspecialistaEspecialidadeMap(Map<Consultorio, List<EspecialistaEspecialidade>> consultorioEspecialistaEspecialidadeMap) {this.consultorioEspecialistaEspecialidadeMap = consultorioEspecialistaEspecialidadeMap;}

    public Map<Especialista, List<Especialidade>> getEspecialistaEspecialidadeMap() {return especialistaEspecialidadeMap;}

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isLogged() {
        return logged;
    }

    public int getQtdConsultasPendentesAprovacao() {return qtdConsultasPendentesAprovacao;}

    public void setQtdConsultasPendentesAprovacao(int qtdConsultasPendentesAprovacao) {this.qtdConsultasPendentesAprovacao = qtdConsultasPendentesAprovacao;}

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public void addEspecialistaEspecialidade(Especialista especialista) {especialistaEspecialidadeMap.put(especialista, especialista.getEspecialidade());}
}
