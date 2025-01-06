package br.ufrn.FormsFramework.exception;

public class FormularioInstanciaException extends DashException {
    String listaErros;

    public FormularioInstanciaException(String listaErros) {
        this.listaErros = listaErros;
    }

    @Override
    public String getMessage() {
        return "Erro ao instanciar formulário: " + listaErros;
    }
    
}
