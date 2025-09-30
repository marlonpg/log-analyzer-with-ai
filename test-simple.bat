@echo off
echo Testing if Java app is running...
curl -s http://localhost:8081/webhook/splunk -X GET 2>nul && echo "✓ App running on 8081" || echo "✗ App not responding on 8081"
echo.
echo If app not running, start with: mvn spring-boot:run