#!/bin/bash

# Usage: execute.sh [WildFly mode] [configuration file]
#
# The default mode is 'standalone' and default configuration is based on the
# mode. It can be 'standalone.xml' or 'domain.xml'.

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_STANDALONE_CONFIG=$JBOSS_HOME/standalone/configuration/
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
JBOSS_MODE=${1:-"standalone"}
JBOSS_CONFIG=${2:-"$JBOSS_MODE.xml"}

function wait_for_server() {
  until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do
    sleep 1
  done
}

echo "=> Starting WildFly server"
$JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -c $JBOSS_CONFIG &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
export STUDENTS_DS="java:/StudentsDS"
export MYSQL_URI="jdbc:mysql://172.18.0.3:3306/STUDENTS_DB"
export MYSQL_USER="root"
export MYSQL_PWD="admin"

$JBOSS_CLI -c << EOF
batch

echo "Connection URL: " $CONNECTION_URL

# Add MySQL module
module add --name=com.mysql --resources=/opt/jboss/wildfly/mysql-connector-java-5.1.32.jar --dependencies=javax.api,javax.transaction.api

# Add MySQL driver
/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)

# Add the datasource
data-source add --name=StudentsDS --driver-name=mysql --jndi-name=$STUDENTS_DS --connection-url=$MYSQL_URI?useUnicode=true&amp;characterEncoding=UTF-8 --user-name=$MYSQL_USER --password=$MYSQL_PWD --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 

# Add a realm based on a database
/subsystem=security/security-domain=jee7-demo-realm:add(cache-type=default)
/subsystem=security/security-domain=jee7-demo-realm/authentication=classic                                         \
   :add(login-modules=[                                                                                      \
           {"code"=>"Database", "flag"=>"required",                                                          \
            "module-options"=>{"dsJndiName" => "$STUDENTS_DS",                                   \
                               "principalsQuery" => "select passwd from STUDENTS_DB.USERS username where username=?",    \
                               "rolesQuery" => "select role, 'Roles' from STUDENTS_DB.USER_ROLES where username=?", \
			       "hashAlgorithm" => "SHA-256", \
			       "hashEncoding" => "base64"}}])

#/subsystem=logging/console-handler=CONSOLE:write-attribute(name=level,value=TRACE)

#/subsystem=logging/logger=org.jboss.security:write-attribute(name=level,value=TRACE)
#Successfully saved resource [ ("subsystem" => "logging"), ("logger" => "org.jboss.security") ]

# Execute the batch
run-batch
EOF

/opt/jboss/wildfly/bin/add-user.sh admin admin 

echo "=> Shutting down WildFly"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=*:shutdown"
fi

