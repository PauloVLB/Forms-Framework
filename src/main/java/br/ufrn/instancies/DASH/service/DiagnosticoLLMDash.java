package br.ufrn.instancies.DASH.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.model.Quesito;
import br.ufrn.FormsFramework.model.Secao;
import br.ufrn.FormsFramework.service.interfaces.FeedbackLLM;
import br.ufrn.instancies.DASH.mapper.llm.GroqLLMResponse;

@Component
public class DiagnosticoLLMDash extends FeedbackLLM {
    public Map<String, String> gerarRespostaLLM(Formulario formulario) {
        String prompt = gerarPrompt(formulario);

        Map<String, String> respostas = new HashMap<>();
        GroqLLMResponse response = (GroqLLMResponse) llmService.getRespostaFromPrompt(prompt);
        respostas.put("content", response.choices().get(0).message().content());

        formulario.setFeedbackLLM(respostas.get("content"));
        
        return respostas;
    }

    public String gerarPrompt(Formulario formulario) {
        String prompt = 
        "Com base no seguinte JSON, que corresponde a um prontuário de um paciente, faça um diagnóstico do paciente. " + 
        "Você não precisa se ater a divisão de seções e quesitos, apenas faça um diagnóstico geral do paciente. " +
        "Seu diagnóstico será avaliado por um médico especialista, que pode ou não concordar com o diagnóstico gerado. " +
        "Portanto, pode dar sugestões de exames, tratamentos, ou qualquer outra informação que julgar relevante. " +
        "Pode assumir que o paciente é real, e que você está fazendo um diagnóstico real.\n" + 
        "Sua mensagem será mostrada ao usuário, deve ser transparente para ele que você está lendo as informações " +
        "de um JSON. Para ele deve ser apenas uma sugestão de diagnóstico.\n" +
        "Além disso, escreva sua resposta como plain text. Não use formatação, nem imagens.\n\n";

        prompt += toJson(formulario);

        return prompt;
    }

    private String toJson(Formulario formulario) {
        StringBuilder json = new StringBuilder("{\n");

        json.append("\t\"nome\": \"").append(formulario.getNome()).append("\",\n");
        json.append("\t\"descricao\": \"").append(formulario.getDescricao()).append("\",\n");
        json.append("\t\"secoes\": [\n");

        for (Secao secao : formulario.getSecoes()) {
            json.append("\t\t{\n");
            json.append("\t\t\t\"nome\": \"").append(secao.getTitulo()).append("\",\n");
            json.append("\t\t\t\"quesitos\": [\n");

            for (Quesito quesito : secao.getQuesitos()) {
                json.append("\t\t\t\t{\n");
                json.append("\t\t\t\t\t\"nome\": \"").append(quesito.getEnunciado()).append("\",\n");
                if(quesito.getResposta() != null) {
                    json.append("\t\t\t\t\t\"resposta\": \"").append(quesito.getResposta().getConteudo()).append("\"\n");
                } else {
                    json.append("\t\t\t\t\t\"resposta\": \"\"\n");
                }
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
