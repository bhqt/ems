#!/bin/bash
set -e
cd /build
apt-get update >/dev/null 2>&1
apt-get install -y maven >/dev/null 2>&1
mvn clean package -DskipTests -B