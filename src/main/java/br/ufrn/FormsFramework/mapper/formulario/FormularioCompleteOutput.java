package br.ufrn.FormsFramework.mapper.formulario;

import java.util.List;

import br.ufrn.FormsFramework.mapper.secao.SecaoCompleteOutput;
import br.ufrn.FormsFramework.model.interfaces.IInformacoesArquivo;

public record FormularioCompleteOutput(
    Long id,    
    String nome, 
    String descricao, 
    Boolean finalizado, 
    Boolean respondido,
    Boolean ehPublico,
    Boolean ehTemplate,
    Long usuarioId,
    List<SecaoCompleteOutput> secoes,
    String feedbackLLM,
    List<Long> instanciasFormularioIds,
    Long formularioPaiId
    ) implements IInformacoesArquivo
{}