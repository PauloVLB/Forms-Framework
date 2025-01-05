package br.ufrn.FormsFramework.mapper.resposta;

import java.util.List;

import br.ufrn.FormsFramework.model.enums.TipoResposta;

public record RespostaCreate(
    List<String>conteudo,
    TipoResposta tipoResposta)
{}
