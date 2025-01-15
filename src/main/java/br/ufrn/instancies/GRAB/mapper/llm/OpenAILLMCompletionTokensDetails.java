package br.ufrn.instancies.GRAB.mapper.llm;

public record OpenAILLMCompletionTokensDetails(
    Long reasoning_tokens,
    Long audio_tokens,
    Long accepted_prediction_tokens,
    Long rejected_prediction_tokens
) {}