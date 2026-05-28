package com.vijay.spring.boot.ai.spring_ai_rag.service;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class IngestionService {

	private final VectorStore vectorStore;

	// Spring AI automatically injects your configured Elasticsearch VectorStore
	// bean
	public IngestionService(VectorStore vectorStore) {
		this.vectorStore = vectorStore;
	}

	public void ingestDocument(final MultipartFile resource) throws IOException {
		
		Resource documentResource = convertMultipartFileToResource(resource);
		// 1. EXTRACT: Read unstructured file types (PDF, txt, docx, etc.)
		TikaDocumentReader documentReader = new TikaDocumentReader(documentResource);
		List<Document> rawDocuments = documentReader.get();

		// 2. TRANSFORM: Slice text safely using the Spring AI 2.x Builder pattern
		TokenTextSplitter textSplitter = TokenTextSplitter.builder().withChunkSize(800).withMinChunkSizeChars(200)
				.withMinChunkLengthToEmbed(5).withMaxNumChunks(10000).withKeepSeparator(true).build();

		List<Document> chunkedDocuments = textSplitter.apply(rawDocuments);

		// 3. LOAD: Automatically calls Google GenAI Embeddings and writes to
		// Elasticsearch
		vectorStore.accept(chunkedDocuments);
	}

	private Resource convertMultipartFileToResource(final MultipartFile file) throws IOException {

		return new InputStreamResource(file.getInputStream()) {

			@Override
			public String getFilename() {
				// Tika uses this filename to help auto-detect the document's MIME type
				return file.getOriginalFilename();
			}

			@Override
			public long contentLength() {
				return file.getSize();
			}
		};
	}
}