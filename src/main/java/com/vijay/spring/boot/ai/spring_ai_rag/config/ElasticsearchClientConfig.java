package com.vijay.spring.boot.ai.spring_ai_rag.config;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.net.ssl.SSLContext;

import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.message.BasicHeader;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.transport.rest5_client.low_level.Rest5Client;

@Configuration
public class ElasticsearchClientConfig {

    @Value("${spring.elasticsearch.uris:https://localhost:9200}")
    private String elasticsearchUri;

    @Value("${spring.elasticsearch.username:elastic}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Bean
    public Rest5Client rest5Client() {
        URI uri = URI.create(elasticsearchUri);
        
        // 1. Generate Basic Authentication Token credentials
        String authPayload = username + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(authPayload.getBytes(StandardCharsets.UTF_8));
        Header[] defaultHeaders = new Header[]{
            new BasicHeader("Authorization", "Basic " + encodedAuth)
        };

        try {
            // 2. Build the standard SSL context that trusts local self-signed certificates
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                    .build();

            // 3. CLEAN & SAFE: Use the native helper setters built directly into Rest5Client
            return Rest5Client.builder(new HttpHost(uri.getScheme(), uri.getHost(), uri.getPort()))
                    .setDefaultHeaders(defaultHeaders)
                    .setSSLContext(sslContext) // Native method handles the async socket engine under the hood
                    .build();
                    
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize authenticated local Elasticsearch secure transport layer", e);
        }
    }
}