package br.ufrn.instancies.GRAB.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.service.interfaces.EstatisticasLLM;

@Component
public class EstatisticasDeFormacaoGrab implements EstatisticasLLM {
    
    public Map<String, String> gerarRespostaLLM(List<Formulario> formularios) {
        return Map.of("content", "GRAB");
    }

    public String gerarPrompt(List<Formulario> formularios) {
        return "GRAB";
    }

}
