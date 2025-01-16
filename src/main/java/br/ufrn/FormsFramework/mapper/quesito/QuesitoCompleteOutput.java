package br.ufrn.FormsFramework.mapper.quesito;

import java.util.List;

import br.ufrn.FormsFramework.mapper.opcao.OpcaoCompleteOutput;
import br.ufrn.FormsFramework.mapper.resposta.RespostaCompleteOutput;
import br.ufrn.FormsFramework.mapper.subItem.ItemOutput;

public record QuesitoCompleteOutput(
    Long id,
    String tipoDeItem,
    String numeracao,
    String enunciado,
    Boolean obrigatorio,
    Integer ordem,
    Integer nivel,
    String tipoResposta,
    Long superQuesitoId,
    Long secaoId,
    RespostaCompleteOutput resposta,
    List<Long> opcoesHabilitadorasIds,
    List<QuesitoCompleteOutput> subQuesitos,
    List<OpcaoCompleteOutput> opcoes
    ) implements ItemOutput
{}