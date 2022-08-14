#!/usr/bin/env bash 

curl http://localhost:8080/sanitize
sleep 1; 
curl http://localhost:8080/xstream;
sleep 1;
curl http://localhost:8080/hello;
sleep 1;
