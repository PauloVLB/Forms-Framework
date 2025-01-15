package br.ufrn.instancies.GRAB.mapper.llm;

public record OpenAILLMPromptTokensDetails(
    Long cached_tokens,
    Long audio_tokens
) {}
