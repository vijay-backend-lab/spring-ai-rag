package com.vijay.spring.boot.ai.spring_ai_rag.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class QuestionAnswerService {

	private final ChatClient chatClient;

	public QuestionAnswerService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
		this.chatClient = chatClientBuilder.defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore)
				.searchRequest(SearchRequest.builder().topK(5).build()).build()).build();
	}

	public String askQuestion(String question) {
		return chatClient.prompt().system("""
				You are an enterprise AI assistant.

				Rules:
				- Answer only from provided context
				- If answer not found, say:
				  "I don't know"
				- Do not hallucinate
				""").user(question).call().content();
	}
}
