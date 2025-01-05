package br.ufrn.DASH.exception;

public class FormularioInconsistenteException extends DashException {
    String listaErros;

    public FormularioInconsistenteException(String listaErros) {
        this.listaErros = listaErros;
    }

    @Override
    public String getMessage() {
        return "Formulário não pode ser finalizado pois se encontra inconsistente. Erros: " + listaErros;
    }
    
}
