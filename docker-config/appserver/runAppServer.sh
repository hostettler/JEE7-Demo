#!/bin/bash
docker run -d  -p 8080:8080 -p 9990:9990 -p 8787:8787 -v /Users/hostettler/tmp/docker-deploy:/opt/jboss/wildfly/standalone/deployments/:rw jee7-demo-wildfly
