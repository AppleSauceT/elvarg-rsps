@echo off
title UberScapes Server

cd /d C:\pvp\elvarg-rsps\ElvargServer

echo Starting UberScapes server...
call gradlew.bat :game:run

pause