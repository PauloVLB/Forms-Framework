package br.ufrn.FormsFramework.mapper.resposta;

import java.util.List;

public record RespostaUpdate(
    List<String>conteudo,
    String tipoResposta)
{}
