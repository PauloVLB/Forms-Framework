package br.ufrn.instancies.JUMP.service;

import org.springframework.stereotype.Service;

import br.ufrn.FormsFramework.mapper.formulario.FormularioCompleteOutput;
import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.service.interfaces.GeracaoArquivo;

@Service
public class GerarIngressoJump implements GeracaoArquivo  {
    @Override
    public FormularioCompleteOutput montarArquivo(Formulario formulario) {
        return null;
    }
}
