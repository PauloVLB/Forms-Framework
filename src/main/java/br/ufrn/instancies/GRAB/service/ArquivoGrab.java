package br.ufrn.instancies.GRAB.service;

import org.springframework.stereotype.Service;

import br.ufrn.FormsFramework.mapper.formulario.FormularioCompleteOutput;
import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.service.interfaces.GeracaoArquivo;

@Service
public class ArquivoGrab implements GeracaoArquivo  {
    @Override
    public FormularioCompleteOutput montarArquivo(Formulario formulario) {
        return null;
    }
}
