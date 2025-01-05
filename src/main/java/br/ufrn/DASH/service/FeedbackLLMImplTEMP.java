package br.ufrn.DASH.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.DASH.model.Formulario;

@Component
public class FeedbackLLMImplTEMP implements FeedbackLLM {
    public Map<String, String> gerarRespostaLLM(Formulario formulario) {
        return Map.of("content", "resposta");
    }

    public String gerarPrompt(Formulario formulario) {
        return null;
    }
    
}
