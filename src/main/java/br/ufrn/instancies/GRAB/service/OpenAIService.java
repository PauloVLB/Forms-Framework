package br.ufrn.instancies.GRAB.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufrn.FormsFramework.mapper.interfaces.ILLMResponse;
import br.ufrn.FormsFramework.service.interfaces.LLMService;
import br.ufrn.instancies.GRAB.mapper.llm.OpenAILLMMessage;
import br.ufrn.instancies.GRAB.mapper.llm.OpenAILLMRequest;
import br.ufrn.instancies.GRAB.mapper.llm.OpenAILLMResponse;
import br.ufrn.instancies.GRAB.mapper.llm.OpenAILLMResponseFormat;

@Service
public class OpenAIService extends LLMService {

    @Override
    public ILLMResponse getRespostaFromPrompt(String prompt) {
        OpenAILLMMessage message = new OpenAILLMMessage("user", prompt);
        OpenAILLMResponseFormat responseFormat = new OpenAILLMResponseFormat("json_object");
        OpenAILLMRequest request = new OpenAILLMRequest(List.of(message), model, 1, responseFormat);

        OpenAILLMResponse response = restTemplate.postForObject(apiUrl, request, OpenAILLMResponse.class);

        return response;
    }
    
}
