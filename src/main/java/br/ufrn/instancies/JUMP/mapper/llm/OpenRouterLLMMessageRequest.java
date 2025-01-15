package br.ufrn.instancies.JUMP.mapper.llm;

public record OpenRouterLLMMessageRequest(
    String role,
    String content
) {}
