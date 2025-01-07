package br.ufrn.instancies.DASH.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.FormsFramework.mapper.formulario.FormularioCompleteOutput;
import br.ufrn.FormsFramework.mapper.formulario.FormularioMapper;
import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.service.interfaces.GeracaoArquivo;

@Service
public class GerarProntuarioPdfDash implements GeracaoArquivo {
    @Autowired
    private FormularioMapper formularioMapper;

    @Override
    public FormularioCompleteOutput montarArquivo(Formulario formulario) {
        FormularioCompleteOutput formularioCompleteOutput = formularioMapper.toFormularioCompleteOutput(formulario);
        return formularioCompleteOutput;
    }
}
