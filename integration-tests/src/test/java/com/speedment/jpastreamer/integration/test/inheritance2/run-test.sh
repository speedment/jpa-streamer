#!/bin/sh

docker-compose rm -v -f -s test-db-2 && docker-compose up -d
mysql -h 127.0.0.1 -P 3304 -u speedment -ppassword testdb2
