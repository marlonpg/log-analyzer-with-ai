package com.loganalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AlertProcessor {
    
    @Autowired
    private SplunkService splunkService;
    
    @Autowired
    private LlamaService llamaService;
    
    @Autowired
    private ActionService actionService;
    
    public void processAlert(Map<String, Object> alert) throws Exception {
        String alertId = (String) alert.get("sid");
        String searchQuery = (String) alert.get("search");
        
        String contextData;
        try {
            contextData = splunkService.getContextData(alertId, searchQuery);
        } catch (Exception e) {
            System.out.println("Splunk unavailable, using mock data: " + e.getMessage());
            contextData = "Mock log data: ERROR 2025-09-29 Connection timeout to database";
        }
        
        String prompt = buildPrompt(alert, contextData);
        String analysis = llamaService.analyze(prompt);
        
        actionService.takeAction(analysis, alert);
    }
    
    private String buildPrompt(Map<String, Object> alert, String contextData) {
        return String.format(
            "Analyze this alert:\nAlert: %s\nContext: %s\nProvide analysis and recommended actions.",
            alert.toString(), contextData
        );
    }
}