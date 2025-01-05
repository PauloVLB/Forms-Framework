package br.ufrn.FormsFramework.service;

import java.util.Map;

import br.ufrn.FormsFramework.model.Formulario;

public interface FeedbackLLM{
    Map<String, String> gerarRespostaLLM(Formulario formulario);
    String gerarPrompt(Formulario formulario);
}
