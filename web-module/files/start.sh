#!/bin/bash

cd /opt/app
export LANG=ru_RU.UTF-8
JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF-8";

exec java ${JAVA_OPTS} -jar demo.cluster-1.0-SNAPSHOT-fat.jar -cluster -conf /opt/app/confSecond.json