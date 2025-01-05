package br.ufrn.FormsFramework.mapper.formulario;

import java.util.List;

import br.ufrn.FormsFramework.mapper.secao.SecaoCompleteOutput;

public record FormularioCompleteOutput(
    Long id,    
    String nome, 
    String descricao, 
    Boolean finalizado, 
    Boolean ehPublico,
    Boolean ehTemplate,
    Long usuarioId,
    List<SecaoCompleteOutput> secoes,
    String feedbackLLM
    )
{}