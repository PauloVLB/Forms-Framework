package br.ufrn.FormsFramework.service.interfaces;

import java.util.List;
import java.util.Map;

import br.ufrn.FormsFramework.model.Formulario;

public interface EstatisticasLLM {
    public abstract Map<String, String> gerarRespostaLLM(List<Formulario> formularios);
    public abstract String gerarPrompt(List<Formulario> formularios);
}