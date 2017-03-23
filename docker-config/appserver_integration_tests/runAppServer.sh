#!/bin/bash

if [[ "$(docker images -q jee7-test-wildfly 2> /dev/null)" == "" ]]; then
 	docker build -t jee7-test-wildfly .
	docker run --name wildfly-test -d  -p 8070:8080 -p 9970:9990 -p 8777:8787  jee7-test-wildfly
else
	docker start wildfly-test
fi
