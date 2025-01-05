package br.ufrn.FormsFramework.exception;

import lombok.Getter;

@Getter
public class OpcaoAlreadyInFeedbackException extends DashException {
    private final Long idFeedback;
    private final Long idOpcao;

    public OpcaoAlreadyInFeedbackException(Long idFeedback, Long idOpcao) {
        super();
        this.idFeedback = idFeedback;
        this.idOpcao = idOpcao;
    }
    
    @Override
    public String getMessage() {
        return "Feedback com id " + idFeedback + " ja contem a opção de id " + idOpcao + ".";
    }
    
}
