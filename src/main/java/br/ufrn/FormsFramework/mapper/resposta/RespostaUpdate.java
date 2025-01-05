package br.ufrn.FormsFramework.mapper.resposta;

import java.util.List;

import br.ufrn.FormsFramework.model.enums.TipoResposta;

public record RespostaUpdate(
    List<String>conteudo,
    TipoResposta tipoResposta)
{}
