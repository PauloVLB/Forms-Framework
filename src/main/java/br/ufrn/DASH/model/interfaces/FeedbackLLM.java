package br.ufrn.DASH.model.interfaces;

import java.util.Map;

import br.ufrn.DASH.model.Formulario;

public interface FeedbackLLM{
    abstract Map<String, String> gerarRespostaLLM(Formulario formulario);
    abstract String gerarPrompt(Formulario formulario);
}
