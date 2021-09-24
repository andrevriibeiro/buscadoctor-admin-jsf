package br.com.devdojo.projetoinicial.persistence.model;

import java.util.Date;
import java.util.Set;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.1
 * @since 1.0.0
 */
public class Fatura extends AbstractEntity {

    private Integer numero;
    private TipoFatura tipoFatura;
    private Paciente paciente;
    private Consultorio consultorio;
    private StatusFatura statusFatura;
    private Faturamento faturamento;
    private Convenio convenio;
    private Date createdAt;
    private Set<Consulta> consulta;
    private double valor;
    private double valorpago;

    public Fatura() {
    }

    private Fatura(Builder builder) {
        id = builder.id;
        numero = builder.numero;
        tipoFatura = builder.tipoFatura;
        paciente = builder.paciente;
        consultorio = builder.consultorio;
        statusFatura = builder.statusFatura;
        faturamento = builder.faturamento;
        convenio = builder.convenio;
        createdAt = builder.createdAt;
        valor = builder.valor;
        valorpago = builder.valorpago;
    }

    public Integer getNumero() {
        return numero;
    }

    public TipoFatura getTipoFatura() {
        return tipoFatura;
    }

    public Consultorio getConsultorio() {
        return consultorio;
    }

    public StatusFatura getStatusFatura() {
        return statusFatura;
    }

    public Faturamento getFaturamento() {
        return faturamento;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setFaturamento(Faturamento faturamento) {
        this.faturamento = faturamento;
    }

    public double getValorpago() {return valorpago;}

    public void setValorpago(double valorpago) {this.valorpago = valorpago;}

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {

        private Integer id;
        private Integer numero;
        private TipoFatura tipoFatura;
        private Paciente paciente;
        private Consultorio consultorio;
        private StatusFatura statusFatura;
        private Faturamento faturamento;
        private Convenio convenio;
        private Date createdAt;
        private double valor;
        private double valorpago;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder paciente(Paciente paciente) {
            this.paciente = paciente;
            return this;
        }

        public Builder numero(Integer numero) {
            this.numero = numero;
            return this;
        }
        public Builder valor (double valor) {
            this.valor = valor;
            return this;
        }

        public Builder tipoFatura(TipoFatura tipoFatura) {
            this.tipoFatura = tipoFatura;
            return this;
        }

        public Builder convenio(Convenio convenio) {
            this.convenio = convenio;
            return this;
        }

        public Builder consultorio(Consultorio consultorio) {
            this.consultorio = consultorio;
            return this;
        }

        public Builder statusFatura(StatusFatura statusFatura) {
            this.statusFatura = statusFatura;
            return this;
        }

        public Builder faturamento(Faturamento faturamento) {
            this.faturamento = faturamento;
            return this;
        }

        public Builder createdAt(Date date) {
            createdAt = date;
            return this;
        }

        public Builder valorpago(double valorpago) {
            valorpago = valorpago;
            return this;
        }

        public Fatura build() {
            return new Fatura(this);
        }
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setTipoFatura(TipoFatura tipoFatura) {
        this.tipoFatura = tipoFatura;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setConsultorio(Consultorio consultorio) {
        this.consultorio = consultorio;
    }

    public void setStatusFatura(StatusFatura statusFatura) {
        this.statusFatura = statusFatura;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Consulta> getConsulta() {
        return consulta;
    }

    public void setConsulta(Set<Consulta> consulta) {
        this.consulta = consulta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}