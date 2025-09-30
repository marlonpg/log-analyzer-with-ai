@echo off
echo Checking Llama server...
echo.
echo Testing port 8080 (llama.cpp server):
curl -s http://localhost:8080/v1/models 2>nul && echo "✓ Llama running on 8080" || echo "✗ No response on 8080"
echo.
echo Testing port 11434 (Ollama):
curl -s http://localhost:11434/api/tags 2>nul && echo "✓ Ollama running on 11434" || echo "✗ No response on 11434"
echo.
echo Testing port 5000 (text-generation-webui):
curl -s http://localhost:5000/api/v1/model 2>nul && echo "✓ text-generation-webui on 5000" || echo "✗ No response on 5000"