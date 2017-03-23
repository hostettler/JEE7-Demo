FROM sebp/elk:latest

ADD ./mysql-connector-java-5.1.32.jar /opt/logstash/lib/

USER root
RUN groupadd -r logstash-logs -g 433 && \
useradd -u 431 -r -g logstash-logs -s /sbin/nologin -c "Logstash container logs" logstash-logs

RUN mkdir -p /opt/logstash/conf.d
RUN chown logstash-logs:logstash-logs /opt/logstash/conf.d

