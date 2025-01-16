package br.ufrn.instancies.JUMP.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.model.Opcao;
import br.ufrn.FormsFramework.model.Quesito;
import br.ufrn.FormsFramework.model.Secao;
import br.ufrn.FormsFramework.service.interfaces.FeedbackLLM;
import br.ufrn.instancies.JUMP.mapper.llm.OpenRouterLLMResponse;

@Component
public class SugestaoLLMJump extends FeedbackLLM {

    public Map<String, String> gerarRespostaLLM(Formulario formulario) {
        String prompt = gerarPrompt(formulario);

        Map<String, String> respostas = new HashMap<>();
        OpenRouterLLMResponse response = (OpenRouterLLMResponse) llmService.getRespostaFromPrompt(prompt);
        respostas.put("content", response.choices().get(0).message().content());
        
        return respostas;
    }

    public String gerarPrompt(Formulario formulario) {

        String prompt = 
        "O seguinte JSON representa respostas obtidas para uma determinada seção Interesses de um formulário." +
        "Essa seção contém opções nas quais os usuários poderão escolher a qual atração está interessado em se inscrever. " +
        "No JSON, respostas em branco (não preenchidas) são representadas por campos vazios. " +
        "Com base nessas respostas, recomende atrações não marcadas que possam ser de interesse de quem respondeu o formulário. " +
        "A resposta que você deve gerar será retornada diretamente ao usuário final, dessa forma, não deixe transparecer o prompt que recebeu. \n\n";

        prompt += toJson(formulario);
        System.out.println(prompt);

        return prompt;
    }

    private String toJson(Formulario formulario) {

        StringBuilder json = new StringBuilder("{\n");
        Formulario formularioBase = formulario; 

        for (int secao_index = 0; secao_index < formularioBase.getSecoes().size(); secao_index++) {
            Secao secao = formularioBase.getSecoes().get(secao_index);

            if(secao.getTitulo().toLowerCase().equals("interesses")) {
                
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

                    
                    Secao secaoFormulario = formulario.getSecoes().get(secao_index);
                    Quesito quesitoFormulario = secaoFormulario.getQuesitos().get(quesito_index);
                    if(quesitoFormulario.getResposta() != null) {
                        json.append("\t\t\t\t\"").append(quesitoFormulario.getResposta().getConteudo()).append("\",\n");
                    } else {
                        json.append("\t\t\t\t\" \",\n");
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
