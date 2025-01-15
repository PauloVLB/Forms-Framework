package br.ufrn.instancies.JUMP.mapper.llm;

import java.util.List;

public record OpenRouterLLMRequest(
    List<OpenRouterLLMMessageRequest> messages,
    String model,
    int temperature
) {}
