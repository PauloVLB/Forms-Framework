package br.ufrn.FormsFramework.mapper.usuario;

import br.ufrn.FormsFramework.model.enums.TipoUsuario;

public record UsuarioCreate(
    String nome,
    String login,
    String senha,
    TipoUsuario tipoUsuario)
{}