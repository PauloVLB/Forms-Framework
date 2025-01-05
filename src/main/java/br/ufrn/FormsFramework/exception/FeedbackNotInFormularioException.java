package br.ufrn.FormsFramework.exception;

import lombok.Getter;

@Getter
public class FeedbackNotInFormularioException extends DashException{
    private final Long idFormulario;
    private final Long idFeedback;

    public FeedbackNotInFormularioException(Long idFormulario, Long idFeedback) {
        super();
        this.idFormulario = idFormulario;
        this.idFeedback = idFeedback;
    }
    
    @Override
    public String getMessage() {
        return "Feedback com id " + idFeedback + " nao encontrado em um formulario com id " + idFormulario + ".";
    }
}