package br.ufrn.instancies.JUMP.mapper.llm;

public record OpenRouterLLMMessage(
    String role,
    String content,
    String refusal
) {}
