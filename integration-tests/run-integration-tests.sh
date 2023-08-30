#!/bin/sh

# Run sakila database 
docker run --platform linux/amd64 -d --publish 3306:3306 --name mysqld restsql/mysql-sakila

# Run inheritance test database 
cd src/test/java/com/speedment/jpastreamer/integration/test/inheritance 
docker-compose rm -v -f -s test-db && docker-compose up -d

# Run second inheritance test database 
cd ../inheritance2 
docker-compose rm -v -f -s test-db-2 && docker-compose up -d
 
echo "Waiting for containers to start..."
sleep 10    

# Run tests 
cd ../../../../../../../../../
mvn clean install 
