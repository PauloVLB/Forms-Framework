package br.ufrn.instancies.JUMP.mapper.llm;

public record OpenRouterLLMMessageResponse(
    String role,
    String content,
    String refusal
) {}
