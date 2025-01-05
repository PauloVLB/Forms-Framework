package br.ufrn.FormsFramework.mapper.quesito;

import br.ufrn.FormsFramework.model.enums.TipoResposta;

public record QuesitoCreate(
    String enunciado,
    Boolean obrigatorio,
    Integer ordem,
    Integer nivel,
    TipoResposta tipoResposta)
{}