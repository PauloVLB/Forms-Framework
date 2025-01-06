package br.ufrn.FormsFramework.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufrn.FormsFramework.model.Formulario;

public abstract class FeedbackLLM{
    @Autowired
    protected LLMService llmService;

    public abstract Map<String, String> gerarRespostaLLM(Formulario formulario);
    public abstract String gerarPrompt(Formulario formulario);
}
