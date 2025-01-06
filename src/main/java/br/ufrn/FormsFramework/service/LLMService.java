package br.ufrn.FormsFramework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.ufrn.FormsFramework.mapper.interfaces.ILLMResponse;

@Service
public abstract class LLMService {

    @Qualifier("llmRestTemplate")
    @Autowired
    protected RestTemplate restTemplate;
    
    @Value("${llm.api-url}")
    protected String apiUrl;

    @Value("${llm.model}")
    protected String model;

    public abstract ILLMResponse getRespostaFromPrompt(String prompt);
}
