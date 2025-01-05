package br.ufrn.FormsFramework.mapper.formulario;

public record FormularioUpdate(
    String nome, 
    String descricao, 
    Boolean finalizado, 
    Boolean ehPublico)
{}