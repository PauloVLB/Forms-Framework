package br.ufrn.instancies.JUMP.mapper.llm;

public record OpenRouterLLMChoice(
    String logprobs,
    String finish_reason,
    Long index,
    OpenRouterLLMMessage message
) 
{}
