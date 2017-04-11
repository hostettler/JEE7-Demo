#! /bin/bash

# s/init/run/g if the image already exists

# init the apach web server
cd apache/
./runWebServer.sh
cd ../

# init the database
cd database/
./runDatabase.sh
cd ../

# init the webserver
cd appserver
./runAppServer.sh
cd ../

# init test webserver
cd appserver_integration_tests/
./runAppServer.sh
cd ../

