package br.ufrn.DASH.mapper.formulario;


public record FormularioCreate(
    String nome, 
    String descricao,
    Boolean ehPublico,
    Boolean ehTemplate)
{}
