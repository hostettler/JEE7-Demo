#!/bin/bash

if [[ "$(docker images -q jee7-logstash-elastic 2> /dev/null)" == "" ]]; then
 	docker build -t jee7-logstash-elastic .
	docker run --name logstash -d   -p 9292:9292   -p 9200:9200  -v /tmp/JEE7-Demo/jee7-search-service/src/main/resources/logstash:/opt/logstash/conf.d  jee7-logstash-elastic
else
	docker start logstash
fi
