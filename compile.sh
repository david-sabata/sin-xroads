#!/bin/bash

mkdir -p build
chmod a+xr build

javac -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar:libs/commons-codec-1.3.jar src/xroads/*.java
javac -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar:libs/commons-codec-1.3.jar src/xroads/agents/*.java
javac -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar:libs/commons-codec-1.3.jar src/xroads/behaviours/*.java
javac -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar:libs/commons-codec-1.3.jar src/xroads/behaviours/carfsm/*.java
javac -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar:libs/commons-codec-1.3.jar src/xroads/behaviours/xroadfsm/*.java
javac -sourcepath src -d build -encoding UTF-8 -classpath libs/jade.jar:libs/commons-codec-1.3.jar src/xroads/gui/*.java

mkdir -p bin

cp libs/jade.jar bin/jade.jar
cp libs/commons-codec-1.3.jar bin/commons-codec-1.3.jar

jar cf bin/sin-project.jar build/xroads/*.class build/xroads/agents/*.class build/xroads/behaviours/*.class build/xroads/behaviours/carfsm/*.class build/xroads/behaviours/xroadfsm/*.class build/xroads/gui/*.class
