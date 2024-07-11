@echo off

:: Démarrer Zookeeper
start powershell -NoExit -Command "cd $PSScriptRoot\..\kafka_2.12; ./bin/windows/zookeeper-server-start.bat ./config/zookeeper.properties"

timeout /t 10 /nobreak

:: Démarrer Kafka
start powershell -NoExit -Command "cd $PSScriptRoot\..\kafka_2.12; ./bin/windows/kafka-server-start.bat ./config/server.properties"
