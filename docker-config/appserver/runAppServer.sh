#!/bin/bash
docker network create --subnet=172.18.0.0/16 pinfosubnet
docker run -d  --network pinfosubnet -p 8080:8080 -p 9990:9990 -p 8787:8787 --ip 172.18.0.3 -v //c/tmp/docker-deploy:/opt/jboss/wildfly/standalone/deployments/:rw jee7-demo-wildfly
