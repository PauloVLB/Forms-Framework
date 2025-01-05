package br.ufrn.FormsFramework.mapper.resposta;

import java.util.List;

import br.ufrn.FormsFramework.mapper.opcao.OpcaoCompleteOutput;

public record RespostaCompleteOutput(
    Long id,
    List<String>conteudo,
    List<OpcaoCompleteOutput> opcoesMarcadas,
    Long idQuesito)
{}
