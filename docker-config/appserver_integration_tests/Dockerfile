FROM jboss/wildfly:latest

ADD ./config_wildfly.sh ./jee7-demo-realm-roles.properties ./jee7-demo-realm-users.properties /opt/jboss/wildfly/
RUN  sed -i_back 's/\r//g' /opt/jboss/wildfly/config_wildfly.sh

RUN ["/opt/jboss/wildfly/config_wildfly.sh"]
RUN rm -rf  /opt/jboss/wildfly/standalone/configuration/standalone_xml_history
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "--debug", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
EXPOSE 8787
EXPOSE 9090
EXPOSE 8080
