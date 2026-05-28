package com.vijay.spring.boot.ai.spring_ai_rag.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vijay.spring.boot.ai.spring_ai_rag.service.QuestionAnswerService;

@RestController
@RequestMapping("/api/v1/rag")
public class QueryController {

	@Autowired
	private QuestionAnswerService questionAnswerService;

	@GetMapping("/query")
	public String askQuestion(@RequestParam("question") String question) {
		if (question == null || question.isEmpty()) {
			return "Prompt cannot be empty.";
		}
		return questionAnswerService.askQuestion(question);
	}
}