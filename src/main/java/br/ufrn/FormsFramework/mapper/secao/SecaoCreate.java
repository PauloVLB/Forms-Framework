package br.ufrn.FormsFramework.mapper.secao;

public record SecaoCreate(
    String titulo, 
    Integer ordem,
    Integer nivel)
{}