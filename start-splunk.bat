@echo off
echo Stopping existing Splunk container...
docker-compose down
echo.
echo Starting Splunk with license acceptance...
docker-compose up -d
echo.
echo Splunk is starting up...
echo Web UI: http://localhost:8000 (admin/changeme)
echo Management API: http://localhost:8089
echo.
echo Wait 2-3 minutes for full startup, then check: http://localhost:8000