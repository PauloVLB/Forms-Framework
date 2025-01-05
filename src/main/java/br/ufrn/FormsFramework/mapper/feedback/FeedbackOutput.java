package br.ufrn.FormsFramework.mapper.feedback;

import java.util.List;

public record FeedbackOutput(
    Long id,
    String descricao,
    Long formularioId,
    List<Long> opcoesMarcadasIds
) 
{}
