package br.ufrn.DASH.mapper.resposta;

import java.util.List;

import br.ufrn.DASH.model.enums.TipoResposta;

public record RespostaCreate(
    List<String>conteudo,
    TipoResposta tipoResposta)
{}
