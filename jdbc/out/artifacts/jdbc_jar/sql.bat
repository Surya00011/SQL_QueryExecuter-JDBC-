@echo off
java -version >nul 2>&1
if errorlevel 1 (
    echo "Java is not installed or not in PATH. Please install Java and try again."
    pause
    exit /b
)
java -jar "jdbc.jar"
pause
