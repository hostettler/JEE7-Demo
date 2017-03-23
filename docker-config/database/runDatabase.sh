#!/bin/bash

if [[ "$(docker images -q jee7-demo-mysql 2> /dev/null)" == "" ]]; then
 	docker build -t jee7-demo-mysql .
	docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin -d jee7-demo-mysql
else
	docker start mysql
fi
