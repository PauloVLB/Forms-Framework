package br.ufrn.DASH.service;

import java.util.Map;

import br.ufrn.DASH.model.Formulario;

public interface FeedbackLLM{
    Map<String, String> gerarRespostaLLM(Formulario formulario);
    String gerarPrompt(Formulario formulario);
}
