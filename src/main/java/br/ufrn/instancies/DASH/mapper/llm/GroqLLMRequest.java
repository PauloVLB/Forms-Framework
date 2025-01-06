package br.ufrn.instancies.DASH.mapper.llm;

import java.util.List;

public record GroqLLMRequest(
    List<GroqLLMMessage> messages,
    String model,
    int temperature
) {}
