package br.ufrn.FormsFramework.mapper.resposta;

import java.util.List;

public record RespostaCreate(
    List<String>conteudo,
    String tipoResposta)
{}
