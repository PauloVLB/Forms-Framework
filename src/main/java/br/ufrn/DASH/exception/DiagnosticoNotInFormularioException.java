package br.ufrn.DASH.exception;

import lombok.Getter;

@Getter
public class DiagnosticoNotInFormularioException extends DashException{
    private final Long idFormulario;
    private final Long idDiagnostico;

    public DiagnosticoNotInFormularioException(Long idFormulario, Long idDiagnostico) {
        super();
        this.idFormulario = idFormulario;
        this.idDiagnostico = idDiagnostico;
    }
    
    @Override
    public String getMessage() {
        return "Diagnostico com id " + idDiagnostico + " nao encontrado em um formulario com id " + idFormulario + ".";
    }
}