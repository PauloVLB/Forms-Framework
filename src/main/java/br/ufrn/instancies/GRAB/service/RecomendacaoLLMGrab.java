package br.ufrn.instancies.GRAB.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.ufrn.FormsFramework.model.Formulario;
import br.ufrn.FormsFramework.service.interfaces.FeedbackLLM;
import br.ufrn.instancies.GRAB.mapper.llm.OpenAILLMResponse;

@Component
public class RecomendacaoLLMGrab extends FeedbackLLM{
    public Map<String, String> gerarRespostaLLM(Formulario formulario){
        String prompt = gerarPrompt(formulario);

        Map<String, String> resposta = new HashMap<>();
        OpenAILLMResponse response = (OpenAILLMResponse) llmService.getRespostaFromPrompt(prompt);
        resposta.put("content", response.choices().get(0).message().content());

        formulario.setFeedbackLLM(resposta.get("content"));
        return resposta;
    }
    public String gerarPrompt(Formulario formulario){
        String prompt = 
        "Considere o título \""+ formulario.getNome() +
        "\"e a descrição: \""+ formulario.getDescricao()+
        "\" de um formulário que é etapa de um processo seletivo para uma vaga de trabalho. " +
        "Dadas essas informações, crie um formulário com perguntas que visam avaliar as qualificações dos candidatos para a vaga. " +
        "Um formulário é organizado em seções, e as seções tem quesitos. " +
        "Elabore quesitos discursivos ou objetivos voltados aos temas ditos na descrição.";

        prompt += "Sua resposta deve ser um JSON atendendo a seguinte estrutura, seguindo ESTRITAMENTE esta estrutura STRICT = TRUE:\n" +
        "{\n" + //
                        "    \"type\": \"object\",\n" + //
                        "    \"properties\": {\n" + //
                        "        \"nome\": {\n" + //
                        "            \"type\": \"string\"\n" + //
                        "        },\n" + //
                        "        \"descricao\": {\n" + //
                        "            \"type\": \"string\"\n" + //
                        "        },\n" + //
                        "        \"secoes\": {\n" + //
                        "            \"type\": \"array\",\n" + //
                        "            \"items\": {\n" + //
                        "                \"type\": \"object\",\n" + //
                        "                \"properties\": {\n" + //
                        "                    \"nome\": {\n" + //
                        "                        \"type\": \"string\"\n" + //
                        "                    },\n" + //
                        "                    \"quesitos\": {\n" + //
                        "                        \"type\": \"array\",\n" + //
                        "                        \"items\": {\n" + //
                        "                            \"type\": \"object\",\n" + //
                        "                            \"properties\": {\n" + //
                        "                                \"nome\": {\n" + //
                        "                                    \"type\": \"string\"\n" + //
                        "                                },\n" + //
                        "                                \"tipoResposta\": {\n" + //
                        "                                    \"type\": \"string\"\n" + //
                        "                                },\n" + //
                        "                                \"opcoes\": {\n" + //
                        "                                    \"type\": \"array\",\n" + //
                        "                                    \"items\": {\n" + //
                        "                                        \"type\": \"object\",\n" + //
                        "                                        \"properties\": {\n" + //
                        "                                            \"nome\": {\n" + //
                        "                                                \"type\": \"string\"\n" + //
                        "                                            }\n" + //
                        "                                        },\n" + //
                        "                                        \"required\": [\"nome\"]\n" + //
                        "                                    }\n" + //
                        "                                }\n" + //
                        "                            },\n" + //
                        "                            \"required\": [\"nome\", \"tipoResposta\"]\n" + //
                        "                        }\n" + //
                        "                    }\n" + //
                        "                },\n" + //
                        "                \"required\": [\"nome\", \"quesitos\"]\n" + //
                        "            }\n" + //
                        "        }\n" + //
                        "    },\n" + //
                        "    \"required\": [\"nome\", \"descricao\", \"secoes\"]\n" + //
                        "}";

        return prompt;
    }

}
