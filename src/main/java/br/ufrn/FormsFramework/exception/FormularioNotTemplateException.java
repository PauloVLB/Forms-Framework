package br.ufrn.FormsFramework.exception;

import lombok.Getter;

@Getter
public class FormularioNotTemplateException extends DashException{
    private final Long id;

    public FormularioNotTemplateException(Long id) {
        super();
        this.id = id;
    }
    
    @Override
    public String getMessage() {
        return "Formulario com id " + id + " possui atributo ehTemplate com o valor falso.";
    }
}
