#!/bin/sh
 
java -Xms128m -Xmx128m -jar /app/ruoyi*.jar #--spring.profiles.active=dev
 
if [ $? != 0 ]; then
  echo Failed to start java >&2
  exit 1
fi