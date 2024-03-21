@echo off
SETLOCAL

cd /d C:\Users\bulga\rnrc

echo Building the project...
mvn clean package
pause
echo Running the application...
java -jar target\partnercheck-0.0.1-SNAPSHOT.jar

ENDLOCAL
pause