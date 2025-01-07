package br.ufrn.instancies.DASH.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.model.Quesito;
import br.ufrn.FormsFramework.model.Secao;
import br.ufrn.instancies.DASH.mapper.llm.GroqLLMResponse;
import br.ufrn.FormsFramework.service.interfaces.EstatisticasLLM;

@Component
public class EstatisticasDeSintomasDash extends EstatisticasLLM {
    
    public Map<String, String> gerarRespostaLLM(List<Formulario> formularios) {
        String prompt = gerarPrompt(formularios);

        Map<String, String> respostas = new HashMap<>();
        GroqLLMResponse response = (GroqLLMResponse) llmService.getRespostaFromPrompt(prompt);
        respostas.put("content", response.choices().get(0).message().content());
        
        return respostas;
    }

    public String gerarPrompt(List<Formulario> formularios) {

        String prompt = 
        "Com base no seguinte JSON, que corresponde a um conjunto de respostas obtidas para um dado formulário, " +
        "faça uma análise das respostas presentes, e forneça estatísticas relevantes sobre os sintomas relatados. " +
        "No JSON, respostas em branco (não preenchidas) são representadas por campos vazios. \n\n";

        prompt += toJson(formularios);
        System.out.println(prompt);

        return prompt;
    }

    private String toJson(List<Formulario> formularios) {
        if (formularios.isEmpty()) {
            return "";
        }

        StringBuilder json = new StringBuilder("{\n");
        Formulario formularioBase = formularios.get(0);
        json.append("\t\"nome\": \"").append(formularioBase.getNome()).append("\",\n");
        json.append("\t\"descricao\": \"").append(formularioBase.getDescricao()).append("\",\n");
        json.append("\t\"secoes\": [\n");

        for (int secao_index = 0; secao_index < formularioBase.getSecoes().size(); secao_index++) {
            Secao secao = formularioBase.getSecoes().get(secao_index);
            json.append("\t\t{\n");
            json.append("\t\t\t\"nome\": \"").append(secao.getTitulo()).append("\",\n");
            json.append("\t\t\t\"quesitos\": [\n");

            for(int quesito_index = 0; quesito_index < secao.getQuesitos().size(); quesito_index++) {
                Quesito quesito = secao.getQuesitos().get(quesito_index);
                json.append("\t\t\t\t{\n");
                json.append("\t\t\t\t\t\"nome\": \"").append(quesito.getEnunciado()).append("\",\n");
                json.append("\t\t\t\t\t\"respostas\": [\n");

                for (Formulario formulario : formularios) {
                    Secao secaoFormulario = formulario.getSecoes().get(secao_index);
                    Quesito quesitoFormulario = secaoFormulario.getQuesitos().get(quesito_index);
                    if(quesitoFormulario.getResposta() != null) {
                        json.append("\t\t\t\t\t\t\t\"").append(quesitoFormulario.getResposta().getConteudo()).append("\",\n");
                    } else {
                        json.append("\t\t\t\t\t\t\t\" \",\n");
                    }
                }

                json.append("\t\t\t\t\t]\n");
                json.append("\t\t\t\t},\n");
            }

            json.append("\t\t\t]\n");
            json.append("\t\t},\n");
        }

        json.append("\t]\n");
        json.append("}");


        return json.toString();
    }

}
