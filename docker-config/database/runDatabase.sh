#!/bin/bash
docker run -p 3306:3306 -d -e MYSQL_ROOT_PASSWORD=admin jee7-demo-mysql
