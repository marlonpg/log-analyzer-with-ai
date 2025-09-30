## AI Agent Bridge

A Java application that receives Splunk alerts, queries for additional context, analyzes with local Llama, and takes automated actions.

### Setup

1. Configure Splunk credentials in `application.properties`
2. Ensure Llama is running locally on port 8080
3. Run: `mvn spring-boot:run`

### Webhook Endpoint

POST to `http://localhost:8081/webhook/splunk` with Splunk alert JSON.

