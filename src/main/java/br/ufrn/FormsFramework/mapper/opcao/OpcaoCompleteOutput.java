package br.ufrn.FormsFramework.mapper.opcao;

public record OpcaoCompleteOutput (
    Long id,
    String textoAlternativa, 
    Integer ordem,
    Long quesitoId)
{}