package com.vijay.spring.boot.ai.spring_ai_rag.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vijay.spring.boot.ai.spring_ai_rag.service.IngestionService;

@RestController
@RequestMapping("/api/v1/rag")
public class IngestionController {

	@Autowired
	private IngestionService ingestionService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file) {
		if (file == null || file.isEmpty()) {
			return ResponseEntity.badRequest().body("No file uploaded or file is empty.");
		}
		try {
			ingestionService.ingestDocument(file);
			return ResponseEntity.ok("Knowledge asset ingested successfully into Elasticsearch!");
		} catch (Exception ex) {
			return ResponseEntity.internalServerError().body("Ingestion failed: " + ex.getMessage());
		}
	}
}