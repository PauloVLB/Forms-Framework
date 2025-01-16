package br.ufrn.instancies.GRAB.mapper.llm;

import java.util.List;

public record OpenAILLMRequest(
    List<OpenAILLMMessage> messages,
    String model,
    int temperature,
    OpenAILLMResponseFormat response_format
) {}
