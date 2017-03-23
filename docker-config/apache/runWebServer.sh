#!/bin/bash

if [[ "$(docker images -q jee7-demo-web 2> /dev/null)" == "" ]]; then
	docker build -t jee7-demo-web .
	docker run --name myapache -d  -p 80:80  -v /tmp/docker-deploy/webapp:/usr/local/apache2/htdocs/ jee7-demo-web
else
	docker start myapache
fi
