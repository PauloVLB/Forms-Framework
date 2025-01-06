package br.ufrn.FormsFramework.service.interfaces;

import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.model.interfaces.IInformacoesArquivo;


public interface GeracaoArquivo {
    <T extends IInformacoesArquivo> T montarArquivo(Formulario formulario);
}
