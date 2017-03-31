#!/bin/bash


docker network inspect jee7-network 1>/dev/null 2>/dev/nukk
if [ $? -ne 0 ]; then
     docker network create --subnet 172.18.0.0/16   jee7-network
fi

if [[ "$(docker images -q jee7-demo-web 2> /dev/null)" == "" ]]; then
	docker build -t jee7-demo-web .
fi
docker run --ip="172.18.0.4" --net="jee7-network" -d  -p 10080:80  -v "//c/tmp/docker-deploy/webapp:/usr/local/apache2/htdocs/" jee7-demo-web
