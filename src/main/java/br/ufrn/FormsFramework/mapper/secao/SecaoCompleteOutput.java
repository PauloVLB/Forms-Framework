package br.ufrn.FormsFramework.mapper.secao;

import java.util.List;

import br.ufrn.FormsFramework.mapper.subItem.ItemOutput;

public record SecaoCompleteOutput(
    Long id,
    String tipoDeItem,
    String numeracao,
    String titulo, 
    Integer ordem, 
    Integer nivel,
    List<ItemOutput> subItens,
    Long superSecaoId,
    Long formularioId) implements ItemOutput
{}
