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

CREATE TABLE publication (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    publication_type VARCHAR(31),
    publishing_date DATE NOT NULL,
    title VARCHAR(255) NOT NULL,
    version INT(6),
    pages INT(6) UNSIGNED,
    url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE author (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    version INT(6),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE publication_author (
    publication_id INT(6) UNSIGNED,
    author_id INT(6) UNSIGNED,
    PRIMARY KEY (publication_id, author_id),
    FOREIGN KEY (publication_id) REFERENCES publication(id),
    FOREIGN KEY (author_id) REFERENCES author(id)
);
