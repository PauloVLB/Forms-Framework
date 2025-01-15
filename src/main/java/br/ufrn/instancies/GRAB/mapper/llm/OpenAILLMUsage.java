package br.ufrn.instancies.GRAB.mapper.llm;

public record OpenAILLMUsage(
    Long prompt_tokens,
    Long completion_tokens,
    Long total_tokens,
    OpenAILLMPromptTokensDetails prompt_tokens_details,
    OpenAILLMCompletionTokensDetails completion_tokens_details
) {}