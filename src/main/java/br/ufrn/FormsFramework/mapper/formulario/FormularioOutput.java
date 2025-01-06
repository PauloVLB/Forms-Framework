package br.ufrn.FormsFramework.mapper.formulario;

import java.util.List;

public record FormularioOutput(
    Long id,    
    String nome, 
    String descricao, 
    Boolean finalizado, 
    Boolean respondido,
    Boolean ehPublico,
    Boolean ehTemplate,
    Long usuarioId,
    List<Long> secoesIds,
    List<Long> feedbacksIds,
    String feedbackLLM,
    List<Long> instanciasFormularioIds,
    Long formularioPaiId
    )
{}