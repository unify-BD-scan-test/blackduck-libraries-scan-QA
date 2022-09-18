# Spring Vulnerable App 

[![CI Pipeline](https://github.com/arvos-dev/spring-vulnerable-app/actions/workflows/build.yaml/badge.svg)](https://github.com/arvos-dev/spring-vulnerable-app/actions/workflows/build.yaml)

## Description 

A simple spring boot app that uses vulnerable dependencies for research purposes.

The vulnerable dependencies being used are : 

- json-sanitizer ( v1.2.0 )
- xstream ( v1.4.17 )

Current endpoints are : 

- /sanitize
- /xstream 
- /hello

## Actions 

The repo comes with a github action that demonstrates the use of [ARVOS](https://github.com/arvos-dev/arvos/) utility tool for dynamic vulnerability analysis.

> **Important** 

 - Dynamic analysis finds vulnerabilities in a runtime environment while the code is being executed. 
 - In case of an idle application, arvos scan will not catch any vulnerability.