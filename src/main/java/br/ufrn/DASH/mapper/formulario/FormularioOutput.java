package br.ufrn.DASH.mapper.formulario;

import java.util.List;

public record FormularioOutput(
    Long id,    
    String nome, 
    String descricao, 
    Boolean finalizado, 
    Boolean ehPublico,
    Boolean ehTemplate,
    Long usuarioId,
    List<Long> secoesIds,
    List<Long> feedbacksIds,
    String feedbackLLM
    )
{}