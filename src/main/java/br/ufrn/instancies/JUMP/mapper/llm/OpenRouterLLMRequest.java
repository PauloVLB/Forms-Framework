package br.ufrn.instancies.JUMP.mapper.llm;

import java.util.List;

public record OpenRouterLLMRequest(
    List<OpenRouterLLMMessage> messages,
    String model,
    int temperature
) {}
