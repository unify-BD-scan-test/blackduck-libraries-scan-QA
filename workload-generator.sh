#!/usr/bin/env bash 

curl http://localhost:8080/vulnerable;
sleep 1; 
curl -o - http://localhost:8080/decompress;
sleep 1;
curl http://localhost:8080/hello;

