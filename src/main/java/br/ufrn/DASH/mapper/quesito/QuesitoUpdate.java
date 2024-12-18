package br.ufrn.DASH.mapper.quesito;

import br.ufrn.DASH.model.enums.TipoResposta;

public record QuesitoUpdate(
    String enunciado,
    Boolean obrigatorio,
    Integer ordem,
    Integer nivel,
    TipoResposta tipoResposta)
{}