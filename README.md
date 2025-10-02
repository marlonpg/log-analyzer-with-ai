## AI Agent Bridge

A Java application that receives Splunk alerts, queries for additional context, analyzes with local Llama, and takes automated actions.

### Setup

1. **Start Splunk**: `start-splunk.bat`
2. **Check Llama**: `check-llama.bat` (should show Ollama on port 11434)
3. **Start Java app**: `mvn spring-boot:run`
4. **Test setup**: `test-simple.bat`
5. **Test webhook**: `test-webhook.bat`

### Scripts

- `start-splunk.bat` - Start Splunk with Docker
- `check-llama.bat` - Check if Llama/Ollama is running
- `start-ollama.bat` - Instructions for Ollama setup
- `test-simple.bat` - Check if Java app is running
- `test-webhook.bat` - Send test alert to webhook

### Webhook Endpoint

POST to `http://localhost:8081/webhook/splunk` with Splunk alert JSON.

