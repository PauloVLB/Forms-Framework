package br.ufrn.instancies.GRAB;

import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.service.FeedbackLLM;

@Component
public class RecomendacaoLLMGrab implements FeedbackLLM {
    public Map<String, String> gerarRespostaLLM(Formulario formulario) {
        return Map.of("content", "GRAB");
    }

    public String gerarPrompt(Formulario formulario) {
        return null;
    }
    
}
