package br.ufrn.FormsFramework.mapper.quesito;

import java.util.List;

public record QuesitoOutput(
    Long id,
    String enunciado,
    Boolean obrigatorio,
    Integer ordem,
    Integer nivel,
    String tipoResposta,
    Long superQuesitoId,
    Long secaoId,
    Long respostaId,
    List<Long> opcoesHabilitadorasIds,
    List<Long> subQuesitosIds,
    List<Long> opcoesIds
    )
{}