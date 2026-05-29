# Spring-AI-RAG Documentation

## Overview
**spring-ai-rag** is a Spring Boot application implementing Retrieval-Augmented Generation (RAG) pipeline using Spring AI, Google Gemini, and Elasticsearch for semantic search and document management.

**Repository:** [vijay-backend-lab/spring-ai-rag](https://github.com/vijay-backend-lab/spring-ai-rag)  
**License:** Apache License 2.0

---

## Highlights
- **RAG Pipeline Implementation** using Spring AI framework
- **Vector Store Integration** with Elasticsearch for semantic search
- **Google Gemini AI** integration for advanced language understanding
- **Document Processing** with Apache Tika support
- **Spring Boot 4.0.6** with Java 25 compatibility
- **Production-Ready Configuration** with environment variable support

---

## Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| **Framework** | Spring Boot | 4.0.6 |
| **Language** | Java | 25 |
| **AI Model** | Google Gemini | 2.5-flash-lite |
| **Vector Store** | Elasticsearch | 9.4.0 |
| **Embeddings** | Google GenAI | text-embedding-004 |
| **Document Processing** | Apache Tika | (via Spring AI) |
| **Build Tool** | Maven | 3.x |
| **Web Framework** | Spring MVC | 4.0.6 |

---

## API Endpoint
The application exposes REST endpoints via Spring MVC. Primary use cases include:
- **Query Processing** - Accept user queries for RAG pipeline
- **Document Ingestion** - Upload and process documents
- **Vector Search** - Retrieve semantically similar documents

*(Specific endpoint details available in controller implementations)*

---

## Configuration

### Application Properties (`application.yaml`)

```yaml
spring:
  application:
    name: spring-ai-rag

  elasticsearch:
    uris: https://localhost:9200/
    username: [configured via environment]
    password: [configured via environment]

  ai:
    model:
      chat:
        enabled: true
        default-classifier: google-genai

    vectorstore:
      elasticsearch:
        index-name: structural-rag-index
        initialize-schema: true

    google:
      genai:
        chat:
          options:
            model: gemini-2.5-flash-lite
            temperature: 0.3
        embedding:
          options:
            model: text-embedding-004
```

---

## Environment Variables

| Variable | Purpose | Example |
|----------|---------|---------|
| `GOOGLE_GENAI_API_KEY` | Google Gemini API authentication | `your-api-key-here` |
| `GOOGLE_GENAI_PROJECT_ID` | Google Cloud Project identifier | `your-project-id` |
| `ELASTICSEARCH_USERNAME` | Elasticsearch authentication | `elastic` |
| `ELASTICSEARCH_PASSWORD` | Elasticsearch password | `[secure-password]` |

---

## Project Structure

```
spring-ai-rag/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/vijay/spring/boot/ai/spring_ai_rag/
│   │   │       ├── [Controllers - REST endpoints]
│   │   │       ├── [Services - Business logic]
│   │   │       ├── [Configuration - Spring beans]
│   │   └── resources/
│   │       └── application.yaml [Configuration file]
│   └── test/
│       └── [Unit and integration tests]
├── pom.xml [Maven dependencies]
├── README.md
└── LICENSE (Apache 2.0)
```

---

## Request Flow

```
User Query
    ↓
REST Controller (Spring MVC)
    ↓
RAG Service Layer
    ├─→ Query Processing (Google Gemini)
    ├─→ Vector Embedding Generation
    ├─→ Elasticsearch Semantic Search
    └─→ Response Generation with Context
    ↓
JSON Response to Client
```

**Flow Steps:**
1. Client sends query via HTTP endpoint
2. Spring Controller routes to RAG service
3. Query is embedded using Google GenAI Embeddings API
4. Elasticsearch retrieves semantically similar documents
5. Gemini LLM generates response using retrieved context
6. Response is returned to client

---

## Build & Run

### Prerequisites
- **Java 25** or higher
- **Maven 3.6+**
- **Elasticsearch 9.x** running (locally or remote)
- **Google Gemini API Key** and Project ID

### Build
```bash
mvn clean package
```

### Run
```bash
export GOOGLE_GENAI_API_KEY=your-api-key
export GOOGLE_GENAI_PROJECT_ID=your-project-id
export ELASTIC_USER=your-elasticsearch-user
export ELASTIC_PASSWORD=your-elasticsearch-password
mvn spring-boot:run
```

---

## Maven Dependencies (Key)
- spring-boot-starter-webmvc
- spring-ai-starter-model-google-genai
- spring-ai-starter-vector-store-elasticsearch
- spring-ai-advisors-vector-store
- spring-ai-tika-document-reader
- elasticsearch-rest5-client

---

## Design Considerations

### 1. **Vector Store Architecture**
- Uses Elasticsearch for distributed, scalable vector storage
- Automatic schema initialization on startup(**dims** should match with embedding model)
- Index: `structural-rag-index` for semantic document storage

### 2. **LLM Model Selection**
- **Google Gemini 2.5 Flash Lite**: Optimized for RAG workloads with lower latency
- **Temperature: 0.3**: Conservative setting for deterministic, factual responses
- **Embedding Model**: `text-embedding-004` for high-quality vector representations

### 3. **Security**
- API keys managed via environment variables (not hardcoded)
- Elasticsearch requires authentication
- Spring Security ready for additional auth layers

### 4. **Scalability**
- Elasticsearch cluster support for distributed indexing
- Asynchronous processing capability via Spring AI advisors
- Stateless REST design enables horizontal scaling

### 5. **Document Processing**
- Apache Tika for multi-format document parsing (PDF, DOCX, TXT, etc.)
- Automatic document chunking and embedding
- Schema auto-provisioning in Elasticsearch

---

## Future Enhancements

- [ ] **Advanced RAG**: Implement re-ranking, query expansion, and graph-based retrieval
- [ ] **Caching Layer**: Redis integration for prompt/response caching
- [ ] **Authentication**: Spring Security with JWT token support
- [ ] **API Documentation**: Swagger/OpenAPI integration
- [ ] **Monitoring**: Prometheus metrics and observability
- [ ] **Batch Processing**: Async document ingestion pipeline
- [ ] **Web UI**: Frontend dashboard for query interface
- [ ] **Database Support**: Persistent metadata storage (PostgreSQL/MySQL)
- [ ] **Cost Optimization**: Token usage tracking and billing integration

---

## Notes

- **Spring AI Version**: 2.0.0-M7 (Milestone Release)
- **Java Compatibility**: Tested with Java 25
- **Elasticsearch Connection**: Uses HTTPS by default
- **Schema Management**: Automatic initialization on application startup
- **Model Router**: Configured to use Google GenAI as default classifier
- **Embedding Consistency**: Ensure same embedding model for indexing and retrieval

---

## License

Licensed under Apache License 2.0

---

## Contribution

Contributions are welcome! Feel free to fork, raise issues, or submit PRs.

---

## Author

**Vijay Kumar**
Backend Engineer | AI | Java | System Design

---

**Last Updated:** May 29, 2026  
**Repository Topics:** `ai` `rag` `rag-pipeline` `spring-ai` `springai`
