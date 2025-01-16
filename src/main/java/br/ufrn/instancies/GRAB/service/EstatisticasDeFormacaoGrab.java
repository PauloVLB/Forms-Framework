package br.ufrn.instancies.GRAB.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.model.Opcao;
import br.ufrn.FormsFramework.model.Quesito;
import br.ufrn.FormsFramework.model.Secao;
import br.ufrn.FormsFramework.service.interfaces.EstatisticasLLM;
import br.ufrn.instancies.GRAB.mapper.llm.OpenAILLMResponse;

@Component
public class EstatisticasDeFormacaoGrab extends EstatisticasLLM {
    
    public Map<String, String> gerarRespostaLLM(List<Formulario> formularios) {
        String prompt = gerarPrompt(formularios);

        Map<String, String> respostas = new HashMap<>();
        OpenAILLMResponse response = (OpenAILLMResponse) llmService.getRespostaFromPrompt(prompt);
        respostas.put("content", response.choices().get(0).message().content());
        
        return respostas;
    }

    public String gerarPrompt(List<Formulario> formularios) {
        
        String prompt = 
        "O seguinte JSON representa um conjuntos de respostas obtidas para uma determinada seção Áreas de Aptidão de um formulário de inscrição para um edital. " +
        "Essa seção pode conter de um a vários quesitos, os quais perguntarão aos candidatos de possíveis habilidades que possuem. " +
        "Com base nessas respostas, coletadas de diferentes formulários, faça uma análise estatística das habilidades informadas pelos candidatos. " +
        "No JSON, respostas em branco (não preenchidas) são representadas por campos vazios. " +
        "A resposta que você deve gerar deverá ser em formato JSON. \n\n";

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

        for (int secao_index = 0; secao_index < formularioBase.getSecoes().size(); secao_index++) {
            Secao secao = formularioBase.getSecoes().get(secao_index);

            if(secao.getTitulo().toLowerCase().equals("áreas de aptidão")) {
                
                json.append("\t\"nome da seção\": \"").append(secao.getTitulo()).append("\",\n");
                json.append("\t\"quesitos\": [\n");

                for(int quesito_index = 0; quesito_index < secao.getQuesitos().size(); quesito_index++) {
                    Quesito quesito = secao.getQuesitos().get(quesito_index);
                    json.append("\t\t{\n");
                    json.append("\t\t\t\"nome\": \"").append(quesito.getEnunciado()).append("\",\n");
                    if(quesito.getOpcoes() != null) {
                        json.append("\t\t\t\"opcoes disponíveis\": [\n");
                        for(Opcao opcao : quesito.getOpcoes()) {
                            json.append("\t\t\t\t\"").append(opcao.getTextoAlternativa()).append("\",\n");
                        }
                        json.append("\t\t\t]\n");
                    }
                    json.append("\t\t\t\"respostas\": [\n");

                    for (Formulario formulario : formularios) {
                        Secao secaoFormulario = formulario.getSecoes().get(secao_index);
                        Quesito quesitoFormulario = secaoFormulario.getQuesitos().get(quesito_index);
                        if(quesitoFormulario.getResposta() != null) {
                            json.append("\t\t\t\t\"").append(quesitoFormulario.getResposta().getConteudo()).append("\",\n");
                        } else {
                            json.append("\t\t\t\t\" \",\n");
                        }
                    }

                    json.append("\t\t\t]\n");
                    json.append("\t\t},\n");
                }

                json.append("\t]\n");

                break;
            }
            
        }

        json.append("}");


        return json.toString();
    }

}
