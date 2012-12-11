#!/bin/sh

java -classpath ./bin/:./bin/jade.jar jade.Boot -gui -local-host 127.0.0.1 XRoadsSpawner:xroads.agents.SpawnerAgent
