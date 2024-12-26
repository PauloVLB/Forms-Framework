package br.ufrn.DASH.exception;

import lombok.Getter;

@Getter
public class FormularioTemplateException extends DashException{
    private final Long id;

    public FormularioTemplateException(Long id) {
        super();
        this.id = id;
    }
    
    @Override
    public String getMessage() {
        return "Formulario com id " + id + " foi definido como template, logo deve permanecer sem respostas.";
    }
}
