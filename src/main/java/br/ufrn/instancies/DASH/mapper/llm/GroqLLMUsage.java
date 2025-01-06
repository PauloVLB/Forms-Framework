package br.ufrn.instancies.DASH.mapper.llm;

public record GroqLLMUsage(
    double queue_time,
    int prompt_tokens,
    double prompt_time,
    int completion_tokens,
    double completion_time,
    int total_tokens,
    double total_time
) {}
