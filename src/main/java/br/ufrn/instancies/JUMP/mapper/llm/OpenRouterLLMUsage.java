package br.ufrn.instancies.JUMP.mapper.llm;

public record OpenRouterLLMUsage(
    Long prompt_tokens,
    Long completion_tokens,
    Long total_tokens
) 
{}
