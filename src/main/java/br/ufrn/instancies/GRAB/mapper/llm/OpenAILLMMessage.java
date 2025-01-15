package br.ufrn.instancies.GRAB.mapper.llm;

public record OpenAILLMMessage(
    String role,
    String content
) {}
