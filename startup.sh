#!/usr/bin/env bash
java -jar -Dspring.profiles.active=${ENVIRONMENT} be-report-0.0.1-SNAPSHOT.jar
