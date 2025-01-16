package br.ufrn.FormsFramework.mapper.quesito;

public record QuesitoUpdate(
    String enunciado,
    Boolean obrigatorio,
    Integer ordem,
    Integer nivel,
    String tipoResposta)
{}