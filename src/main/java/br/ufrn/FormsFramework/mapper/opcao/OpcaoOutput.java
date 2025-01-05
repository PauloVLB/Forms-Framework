package br.ufrn.FormsFramework.mapper.opcao;

import java.util.List;

public record OpcaoOutput (
    Long id,
    String textoAlternativa, 
    Integer ordem,
    List<Long> feedbacksIds,
    Long quesitoId)
{}