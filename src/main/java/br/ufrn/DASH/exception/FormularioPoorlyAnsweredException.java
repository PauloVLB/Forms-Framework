package br.ufrn.DASH.exception;

import lombok.Getter;

@Getter
public class FormularioPoorlyAnsweredException extends DashException{
    String listaErros;

    public FormularioPoorlyAnsweredException(String listaErros) {
        this.listaErros = listaErros;
    }
    
    @Override
    public String getMessage() {
        return "Alguns campos do formulário foram respondidos incorretamente. Erros: " + listaErros;
    }
}
