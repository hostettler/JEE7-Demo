#! /bin/bash

# s/init/run/g if the image already exists

# init the apach web server
./apache/runWebServer.sh

# init the database
./database/runDatabase.sh

# init the webserver
./appserver/runAppServer.sh

# init test webserver
./appserver_integration_tests/runAppServer.sh

