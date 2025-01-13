package br.ufrn.FormsFramework.mapper.resposta;

import java.util.List;

import br.ufrn.FormsFramework.model.enums.TipoResposta;

public record RespostaOutput(
    Long id,
    List<String>conteudo,
    List<Long> opcoesMarcadasIds,
    Long idQuesito,
    TipoResposta tipoResposta)
{}
