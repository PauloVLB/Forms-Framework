package br.ufrn.FormsFramework.service.interfaces;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufrn.FormsFramework.model.Formulario;

public abstract class EstatisticasLLM {
    @Autowired
    protected LLMService llmService;
    
    public abstract Map<String, String> gerarRespostaLLM(List<Formulario> formularios);
    public abstract String gerarPrompt(List<Formulario> formularios);
}