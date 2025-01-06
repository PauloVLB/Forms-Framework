package br.ufrn.instancies.DASH.mapper.llm;

import java.util.List;

import br.ufrn.FormsFramework.mapper.interfaces.ILLMResponse;

public record GroqLLMResponse(
    String id,
    String object,
    Long created,
    String model,
    List<GroqLLMChoice> choices,
    GroqLLMUsage usage,
    String system_fingerprint,
    LLMGroq x_groq
) implements ILLMResponse
{}
