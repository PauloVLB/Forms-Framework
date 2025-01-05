package br.ufrn.FormsFramework.mapper.formulario;


public record FormularioCreate(
    String nome, 
    String descricao,
    Boolean ehPublico,
    Boolean ehTemplate)
{}
