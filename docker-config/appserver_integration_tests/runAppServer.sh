#!/bin/bash

if [[ "$(docker images -q jee7-test-wildfly 2> /dev/null)" == "" ]]; then
 	docker build -t jee7-test-wildfly .
fi
docker run -d  -p 8080:8080 -p 9990:9990 -p 8777:8787  jee7-test-wildfly
