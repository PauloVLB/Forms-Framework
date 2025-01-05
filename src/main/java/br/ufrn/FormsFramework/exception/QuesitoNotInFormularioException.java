package br.ufrn.FormsFramework.exception;

import lombok.Getter;

@Getter
public class QuesitoNotInFormularioException extends DashException{
    private final Long idFormulario;
    private final Long idQuesito;

    public QuesitoNotInFormularioException(Long idFormulario, Long idQuesito) {
        super();
        this.idFormulario = idFormulario;
        this.idQuesito = idQuesito;
    }
    
    @Override
    public String getMessage() {
        return "Quesito com id " + idQuesito + " nao se encontra no formulario com id " + idFormulario + ".";
    }
}