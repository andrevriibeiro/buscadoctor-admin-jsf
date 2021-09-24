package br.com.devdojo.projetoinicial.persistence.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.1
 * @since 1.0.0
 */
public class ConsultorioConvenio extends AbstractEntity {

    private Convenio convenio;

    private Consultorio consultorio;

    private Integer codPrestador;

    public ConsultorioConvenio() {
    }

    private ConsultorioConvenio(Builder builder) {
        setId(builder.id);
        convenio = builder.convenio;
        consultorio = builder.consultorio;
        codPrestador = builder.codPrestador;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public Integer getCodPrestador() {
        return codPrestador;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public void setCodPrestador(Integer codPrestador) {
        this.codPrestador = codPrestador;
    }

    @Override
    public String toString() {
        return "ConsultorioConvenio{" +
                "convenio=" + convenio +
                ", consultorio=" + consultorio +
                ", codPrestador=" + codPrestador +
                "} " + super.toString();
    }

    public static final class Builder {
        private Integer id;
        private Convenio convenio;
        private Consultorio consultorio;
        private Integer codPrestador;

        private Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder convenio(Convenio val) {
            convenio = val;
            return this;
        }

        public Builder consultorio(Consultorio val) {
            consultorio = val;
            return this;
        }

        public Builder codPrestador(Integer val) {
            codPrestador = val;
            return this;
        }

        public ConsultorioConvenio build() {
            return new ConsultorioConvenio(this);
        }
    }
}