package br.ufrn.instancies.DASH.mapper.llm;

public record GroqLLMMessage(
    String role,
    String content
) {}
