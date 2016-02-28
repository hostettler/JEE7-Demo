FROM mysql:latest
MAINTAINER steve.hostettler@gmail.com

COPY ./100_schema.sql ./200_init_data.sql /docker-entrypoint-initdb.d/
