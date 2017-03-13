#!/bin/bash
docker network create --subnet=172.18.0.0/16 pinfosubnet
docker run -p 3306:3306 -d --network pinfosubnet  --ip 172.18.0.2 -e MYSQL_ROOT_PASSWORD=admin jee7-demo-mysql
