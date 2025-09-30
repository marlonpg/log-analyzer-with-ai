package com.loganalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookController {
    
    @Autowired
    private AlertProcessor alertProcessor;
    
    @PostMapping("/splunk")
    public ResponseEntity<String> handleSplunkAlert(@RequestBody Map<String, Object> alert) {
        try {
            alertProcessor.processAlert(alert);
            return ResponseEntity.ok("Alert processed");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}