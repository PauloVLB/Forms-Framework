package br.ufrn.FormsFramework.mapper.usuario;

import java.util.List;

import br.ufrn.FormsFramework.model.enums.TipoUsuario;

public record UsuarioOutput(
    Long id,
    String nome,
    String login,
    String senha,
    TipoUsuario tipoUsuario,
    List<Long> formulariosIds)
{}