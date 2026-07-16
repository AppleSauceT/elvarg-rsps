@echo off
title UberScapes Launcher

echo Starting UberScapes server...

start "UberScapes Server" cmd /k ^
"cd /d C:\pvp\elvarg-rsps\ElvargServer && gradlew.bat :game:run"

echo Waiting for the server to load...
timeout /t 8 /nobreak >nul

echo Starting UberScapes client...

start "UberScapes Client" cmd /k ^
"cd /d C:\pvp\elvarg-rsps\ElvargClient && gradlew.bat run"

exit