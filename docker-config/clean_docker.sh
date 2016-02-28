#!/bin/bash
docker rmi -f `docker images | grep "^<none>" | cut -c41-52`
docker rmi $(docker images -q -f dangling=true)
docker rm -v $(echo $(docker ps -q --no-trunc) $(docker ps -a -q --no-trunc) | sed 's|\s|\n|g'  | sort | uniq -u)
