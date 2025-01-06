package br.ufrn.instancies.DASH.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.service.FeedbackLLM;

@Component
public class DiagnosticoLLMDash implements FeedbackLLM {
    public Map<String, String> gerarRespostaLLM(Formulario formulario) {
        return Map.of("content", "DASH");
    }

    public String gerarPrompt(Formulario formulario) {
        return null;
    }
    
}
