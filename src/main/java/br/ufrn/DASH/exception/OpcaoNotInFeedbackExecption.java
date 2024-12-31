package br.ufrn.DASH.exception;

public class OpcaoNotInFeedbackExecption extends DashException{
    private final Long idFeedback;
    private final Long idOpcao;

    public OpcaoNotInFeedbackExecption(Long idFeedback, Long idOpcao) {
        super();
        this.idOpcao = idOpcao;
        this.idFeedback = idFeedback;
    }
    
    @Override
    public String getMessage() {
        return "Opcao com id " + idFeedback + " nao encontrada em um feedback com id " + idOpcao + ".";
    }
}