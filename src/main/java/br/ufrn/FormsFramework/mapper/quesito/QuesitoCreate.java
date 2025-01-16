package br.ufrn.FormsFramework.mapper.quesito;

public record QuesitoCreate(
    String enunciado,
    Boolean obrigatorio,
    Integer ordem,
    Integer nivel,
    String tipoResposta)
{}