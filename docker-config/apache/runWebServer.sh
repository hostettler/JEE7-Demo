#!/bin/bash
docker run -d  -p 80:80  -v /tmp/docker-deploy/webapp:/usr/local/apache2/htdocs/ jee7-demo-web
