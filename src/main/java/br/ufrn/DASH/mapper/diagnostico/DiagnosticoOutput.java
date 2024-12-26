package br.ufrn.DASH.mapper.diagnostico;

import java.util.List;

public record DiagnosticoOutput(
    Long id,
    String descricao,
    Long formularioId,
    List<Long> opcoesMarcadasIds
) 
{}
