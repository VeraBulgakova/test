@echo off
SETLOCAL

cd /d C:\Users\bulga\rnrc

echo Building the project...
call mvn clean package

echo Running the application...
call java -jar target\partnercheck-0.0.1-SNAPSHOT.jar

ENDLOCAL
pause