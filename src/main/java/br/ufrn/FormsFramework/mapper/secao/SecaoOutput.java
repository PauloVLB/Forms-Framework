package br.ufrn.FormsFramework.mapper.secao;

import java.util.List;

public record SecaoOutput(
    Long id,
    String titulo, 
    Integer ordem, 
    Integer nivel,
    List<Long> subSecoesIds,
    Long superSecaoId,
    Long formularioId,
    List<Long> quesitosIds)
{}
