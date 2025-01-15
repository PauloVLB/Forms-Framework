package br.ufrn.instancies.JUMP.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.ufrn.FormsFramework.mapper.interfaces.ILLMResponse;
import br.ufrn.FormsFramework.service.interfaces.LLMService;
import br.ufrn.instancies.JUMP.mapper.llm.OpenRouterLLMMessage;
import br.ufrn.instancies.JUMP.mapper.llm.OpenRouterLLMRequest;
import br.ufrn.instancies.JUMP.mapper.llm.OpenRouterLLMResponse;

@Service
public class OpenRouterDeepSeekService extends LLMService {

    @Override
    public ILLMResponse getRespostaFromPrompt(String prompt) {
        OpenRouterLLMMessage message = new OpenRouterLLMMessage("user", prompt, "");
        OpenRouterLLMRequest request = new OpenRouterLLMRequest(List.of(message), model, 1);

        OpenRouterLLMResponse response = restTemplate.postForObject(apiUrl, request, OpenRouterLLMResponse.class);

        return response;
    }
    
}
