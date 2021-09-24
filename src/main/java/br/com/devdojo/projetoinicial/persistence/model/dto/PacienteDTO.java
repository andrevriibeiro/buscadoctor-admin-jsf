package br.com.devdojo.projetoinicial.persistence.model.dto;

import br.com.devdojo.projetoinicial.persistence.model.AbstractEntity;
import br.com.devdojo.projetoinicial.persistence.model.Paciente;
import br.com.devdojo.projetoinicial.persistence.model.UsuarioConvenio;

import java.util.List;

/**
 * Created by Andre Ribeiro on 20/05/17.
 */
public class PacienteDTO extends AbstractEntity {

    private Paciente paciente;
    private List<UsuarioConvenio> usuarioConvenios;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<UsuarioConvenio> getUsuarioConvenios() {
        return usuarioConvenios;
    }

    public void setUsuarioConvenios(List<UsuarioConvenio> usuarioConvenios) {
        this.usuarioConvenios = usuarioConvenios;
    }
}
