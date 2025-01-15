package br.ufrn.instancies.JUMP.mapper.llm;

import java.util.List;

import br.ufrn.FormsFramework.mapper.interfaces.ILLMResponse;


public record OpenRouterLLMResponse(
        String id,
        String provider,
        String model,
        String object,
        Long created,
        List<OpenRouterLLMChoice> choices,
        String system_fingerprint,
        OpenRouterLLMUsage usage
) implements ILLMResponse
{}
