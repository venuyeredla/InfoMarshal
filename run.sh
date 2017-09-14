#!/bin/bash	
mvn package -DskipTests=true &&  java -jar target/Application.jar 
