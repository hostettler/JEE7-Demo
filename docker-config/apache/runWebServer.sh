#!/bin/bash
docker run -d  -p 80:80  -v /Users/hostettler/tmp/docker-deploy/webapp:/usr/local/apache2/htdocs/ jee7-demo-web
