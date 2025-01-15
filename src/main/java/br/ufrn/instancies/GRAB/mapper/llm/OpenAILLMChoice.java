package br.ufrn.instancies.GRAB.mapper.llm;

public record OpenAILLMChoice(
    int index,
    OpenAILLMMessage message,
    String logprobs,
    String finish_reason
) {}
