package br.ufrn.FormsFramework.exception;

public class EstatisticasLLMException extends DashException {
    String listaErros;

    public EstatisticasLLMException(String listaErros) {
        this.listaErros = listaErros;
    }

    @Override
    public String getMessage() {
        return "Erro ao gerar estatísticas LLM: " + listaErros;
    }
    
}
