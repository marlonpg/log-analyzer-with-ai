package com.loganalyzer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service
public class SplunkService {
    
    @Value("${splunk.host:localhost}")
    private String splunkHost;
    
    @Value("${splunk.port:8089}")
    private int splunkPort;
    
    @Value("${splunk.username:admin}")
    private String username;
    
    @Value("${splunk.password:changeme}")
    private String password;
    
    private final HttpClient httpClient = HttpClient.newHttpClient();
    
    public String getContextData(String alertId, String searchQuery) throws Exception {
        String auth = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(String.format("http://%s:%d/services/search/jobs/export", splunkHost, splunkPort)))
            .header("Authorization", "Basic " + auth)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString("search=" + java.net.URLEncoder.encode(searchQuery, "UTF-8") + "&output_mode=json"))
            .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}