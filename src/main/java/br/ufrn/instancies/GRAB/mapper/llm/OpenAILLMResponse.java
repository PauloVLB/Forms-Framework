package br.ufrn.instancies.GRAB.mapper.llm;

import java.util.List;

import br.ufrn.FormsFramework.mapper.interfaces.ILLMResponse;

public record OpenAILLMResponse(
    String id,
    String object,
    Long created,
    String model,
    List<OpenAILLMChoice> choices,
    OpenAILLMUsage usage,
    String service_tier,
    String system_fingerprint
) implements ILLMResponse
{}
