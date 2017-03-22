#!/bin/bash

if [[ "$(docker images -q jee7-demo-wildfly 2> /dev/null)" == "" ]]; then
 	docker build -t jee7-demo-wildfly .
	docker run --name mywildfly --link mysql:db -d  -p 8080:8080 -p 9990:9990 -p 8787:8787 -v /tmp/docker-deploy:/opt/jboss/wildfly/standalone/deployments/:rw jee7-demo-wildfly
else
	docker start mywildfly 
fi
