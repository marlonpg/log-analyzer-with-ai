package com.loganalyzer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class LlamaService {
    
    @Value("${llama.url:http://localhost:11434}")
    private String llamaUrl;
    
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public String analyze(String prompt) throws Exception {
        // Try Ollama format first (most common)
        if (llamaUrl.contains("11434")) {
            return analyzeWithOllama(prompt);
        }
        // Fallback to OpenAI-compatible format
        return analyzeWithOpenAI(prompt);
    }
    
    private String analyzeWithOllama(String prompt) throws Exception {
        Map<String, Object> requestBody = Map.of(
            "model", "llama2",
            "prompt", prompt,
            "stream", false
        );
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(llamaUrl + "/api/generate"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
            .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, Object> responseBody = objectMapper.readValue(response.body(), Map.class);
        return (String) responseBody.get("response");
    }
    
    private String analyzeWithOpenAI(String prompt) throws Exception {
        Map<String, Object> requestBody = Map.of(
            "prompt", prompt,
            "max_tokens", 500,
            "temperature", 0.7
        );
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(llamaUrl + "/v1/completions"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
            .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, Object> responseBody = objectMapper.readValue(response.body(), Map.class);
        return (String) ((Map<String, Object>) ((java.util.List<?>) responseBody.get("choices")).get(0)).get("text");
    }
}