--
-- JPAstreamer - Express JPA queries with Java Streams
-- Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
--
-- License: GNU Lesser General Public License (LGPL), version 2.1 or later.
--
-- This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
-- without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
-- See the GNU Lesser General Public License for more details.
--
-- See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
--

CREATE TABLE books (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    publishing_date DATE NOT NULL,
    title VARCHAR(255) NOT NULL,
    version INT(6),
    pages INT(6),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE blogpost (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    publishing_date DATE NOT NULL,
    title VARCHAR(255) NOT NULL,
    version INT(6),
    url VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE author (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    version INT(6),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
