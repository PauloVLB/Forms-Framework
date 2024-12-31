package br.ufrn.DASH.exception;

import lombok.Getter;

@Getter
public class FeedbackAndOpcaoIncompatibleException extends DashException{
    private final Long idFeedback;
    private final Long idOpcao;

    public FeedbackAndOpcaoIncompatibleException(Long idFeedback, Long idOpcao) {
        super();
        this.idFeedback = idFeedback;
        this.idOpcao = idOpcao;
    }
    
    @Override
    public String getMessage() {
        return "Opcao com id " + idOpcao + " nao se encontra no mesmo formulario que o Feedback com id " + idFeedback + ".";
    }
}
