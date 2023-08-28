#!/bin/sh

docker-compose rm -v -f -s test-db && docker-compose up -d
mysql -h 127.0.0.1 -P 3305 -u speedment -ppassword publications
