#!/bin/bash

# Usage: execute.sh [WildFly mode] [configuration file]
#
# The default mode is 'standalone' and default configuration is based on the
# mode. It can be 'standalone.xml' or 'domain.xml'.

JBOSS_HOME=/opt/jboss/wildfly
JBOSS_CUSTOMIZATION=$JBOSS_HOME/customization
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
export H2_URI="jdbc:h2:mem:STUDENTS_DB;DB_CLOSE_DELAY=-1"
export H2_USER="sa"
export H2_PWD="sa"

$JBOSS_CLI -c << EOF
batch

echo "Connection URL: " $CONNECTION_URL

# Add the datasource
data-source add --name=StudentsDS --driver-name=h2 --jndi-name=$STUDENTS_DS --connection-url=$H2_URI --user-name=$H2_USER --password=$H2_PWD --use-ccm=false --max-pool-size=25 --blocking-timeout-wait-millis=5000 

# Add a realm based on a database
/subsystem=security/security-domain=jee7-demo-realm:add(cache-type=default)
/subsystem=security/security-domain=jee7-demo-realm/authentication=classic:add()
/subsystem=security/security-domain=jee7-demo-realm/authentication=classic/login-module=UsersRoles       \
    :add(code=UsersRoles, flag=required,                                                        \
         module-options={"usersProperties"=>"${JBOSS_CUSTOMIZATION}/jee7-demo-realm-users.properties",   \
                         "rolesProperties"=>"${JBOSS_CUSTOMIZATION}/jee7-demo-realm-roles.properties"})


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

