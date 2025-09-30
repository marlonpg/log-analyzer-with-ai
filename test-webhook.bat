@echo off
echo Testing webhook endpoint...
curl -X POST http://localhost:8081/webhook/splunk ^
  -H "Content-Type: application/json" ^
  -d "{\"sid\":\"test123\",\"search\":\"index=main error\",\"alert\":\"High error rate detected\"}"
echo.
echo Check application logs for processing details.