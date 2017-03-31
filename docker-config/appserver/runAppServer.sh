#!/bin/bash

docker network inspect jee7-network 1>/dev/null 2>/dev/nukk
if [ $? -ne 0 ]; then
     docker network create --subnet 172.18.0.0/16   jee7-network
fi

if [[ "$(docker images -q jee7-demo-wildfly 2> /dev/null)" == "" ]]; then
 	docker build -t jee7-demo-wildfly .
fi
docker run --ip="172.18.0.2" --net="jee7-network" -d  -p 18080:8080 -p 19990:9990 -p 18787:8787 -v "//c/tmp/docker-deploy:/opt/jboss/wildfly/standalone/deployments/:rw" jee7-demo-wildfly
