#!/bin/bash

echo "Initialize MySql with root password=$MYSQL_ROOT_PASSWORD"

/entrypoint.sh mysqld $@  &
if [ $? -ne 0 ];
then
   echo "MySql initialization failed!"
   exit 100
fi
echo "jobs in bg"
jobs

echo "Wait for MySql to start"
sleep 10
until mysqladmin ping &>/dev/null; do
  echo -n "."; sleep 1
done
echo "MySql Successfully Started"

# create the default database from the ADDed file.
echo "Create the database."
mysql --user=root --password=$MYSQL_ROOT_PASSWORD < /docker-entrypoint-initdb.d/100_schema.sql
mysql --user=root --password=$MYSQL_ROOT_PASSWORD < /docker-entrypoint-initdb.d/200_init_data.sql

# Put MySqld in foreground so that the container does not stop at the end
bash
