#!/usr/bin/bash

echo "WARNING: this file should only be ran by docker"
/app/cloud-sql-proxy --credentials-file /app/jazz-gae-proxy.json --address 0.0.0.0 --port 5432 jazz-383505:us-west1:main &
sleep 5
java -jar /app/main.jar
