package br.com.devdojo.projetoinicial.persistence.model;

/**
 * @author Guilherme Mendes <gmendes92@gmail.com>
 * @version 1.1.0
 * @since 1.0.0
 */
public class FaturaProcedimento extends AbstractEntity {

    private Fatura fatura;
    private ProcedimentoEspecialista procedimentoEspecialista;
    private int quantidade;
    private int guia;
    private Consulta consulta;

    public FaturaProcedimento() {
    }

    private FaturaProcedimento(Builder builder) {
        id = builder.id;
        fatura = builder.fatura;
        procedimentoEspecialista = builder.procedimentoEspecialista;
        quantidade = builder.quantidade;
        guia = builder.guia;
        consulta = builder.consulta;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public ProcedimentoEspecialista getProcedimentoEspecialista() {
        return procedimentoEspecialista;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getGuia() {
        return guia;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final static class Builder {

        private Integer id;
        private Fatura fatura;
        private ProcedimentoEspecialista procedimentoEspecialista;
        private int quantidade;
        private int guia;
        private Consulta consulta;

        public Builder() {
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder fatura(Fatura fatura) {
            this.fatura = fatura;
            return this;
        }

        public Builder procedimentoEspecialista(ProcedimentoEspecialista procedimentoEspecialista) {
            this.procedimentoEspecialista = procedimentoEspecialista;
            return this;
        }

        public Builder quantidade(int quantidade) {
            this.quantidade = quantidade;
            return this;
        }

        public Builder guia(int guia) {
            this.guia = guia;
            return this;
        }

        public Builder consulta(Consulta consulta) {
            this.consulta = consulta;
            return this;
        }

        public FaturaProcedimento build() {
            return new FaturaProcedimento(this);
        }
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }

    public void setProcedimentoEspecialista(ProcedimentoEspecialista procedimentoEspecialista) {
        this.procedimentoEspecialista = procedimentoEspecialista;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setGuia(int guia) {
        this.guia = guia;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }
}
