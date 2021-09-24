package br.com.devdojo.projetoinicial.persistence.model.enums;


import java.util.HashMap;
import java.util.Map;

/**
 * @author Andre Ribeiro, Andre Ribeiro on 18/04/17.
 */
public enum StatusConsultaEnum {

    DISPONIVEL(1, "Disponível", true),
    PENDENTE_APROVAÇÃO(2, "Aprovação pendente", true),
    APROVADA(3, "Aprovada", true),
    REJEITADA(4, "Rejeitada", false),
    CANCELADA(5, "Cancelada", false),
    AGUARDANDO_ATENDIMENTO(6, "Aguardando atendimento", true),
    EM_ATENDIMENTO(7, "Em atendimento", true),
    ATENDIDO(8, "Atendido", true),
    NAO_COMPARECEU(9, "Não compareceu", true),
    REMARCADO(10, "Remarcado", false),
    CONFIRMADO(11, "Confirmado", true),
    NAO_HAVERA_ATENDIMENTO(12, "Não haverá atendimento", false),
    PENDENTE_CANCELAMENTO(13, "Cancelamento pendente", true),
    PENDENTE_CONFIRMACAO(14, "Confirmação pendente", true);
    private int id;
    private String label;
    private boolean exibirNoAgendamento;

    StatusConsultaEnum(int status, String label, boolean exibirNoAgendamento) {
        this.id = status;
        this.label = label;
        this.exibirNoAgendamento = exibirNoAgendamento;
    }

    private static Map<Integer, StatusConsultaEnum> map = new HashMap<>();

    static {
        for (StatusConsultaEnum status : StatusConsultaEnum.values()) {
            map.put(status.getId(), status);
        }
    }

    public static StatusConsultaEnum getEnumByStatusId(Integer id) {
        return map.get(id);
    }

    public boolean isExibirNoAgendamento() {
        return exibirNoAgendamento;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
}
