package br.ufrn.instancies.DASH.mapper.llm;

public record GroqLLMChoice(
    int index,
    GroqLLMMessage message,
    String logprobs,
    String finish_reason
) {}
