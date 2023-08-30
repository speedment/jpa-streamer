#!/bin/sh
#
# JPAstreamer - Express JPA queries with Java Streams
# Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
#
# License: GNU Lesser General Public License (LGPL), version 2.1 or later.
#
# This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
# without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
# See the GNU Lesser General Public License for more details.
#
# See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
#


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
