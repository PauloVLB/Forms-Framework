package br.ufrn.FormsFramework.mapper.resposta;

import java.util.List;

public record RespostaOutput(
    Long id,
    List<String>conteudo,
    List<Long> opcoesMarcadasIds,
    Long idQuesito,
    String tipoResposta)
{}
