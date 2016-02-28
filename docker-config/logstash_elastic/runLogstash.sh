#!/bin/bash
docker run -d   -p 9292:9292   -p 9200:9200  -v /Users/hostettler/git/JEE7-Demo/jee7-search-service/src/main/resources/logstash:/opt/logstash/conf.d  jee7-logstash-elastic
