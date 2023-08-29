INSERT INTO publication(id, publication_type, publishing_date, title, version, pages, url)
VALUES  (1, 'Book', '2008-7-04', 'Book 1', 2, 213, null),
        (2, 'Book', '2009-7-04', 'Book 2', 2, 234, null),
        (3, 'Book', '2010-7-04', 'Book 3', 2, 643, null),
        (4, 'Book', '2011-7-04', 'Book 4', 2, 211, null),
        (5, 'Book', '2012-7-04', 'Book 5', 2, 887, null),
        (6, 'Book', '2013-7-04', 'Book 6', 2, 123, null),
        (7, 'Book', '2014-7-04', 'Book 7', 2, 312, null),
        (8, 'BlogPost', '2015-7-04', 'BlogPost 1', 2, null, 'www.speedment.com'),
        (9, 'BlogPost', '2016-7-04', 'BlogPost 2', 2, null, 'www.speedment.com'),
        (10, 'BlogPost', '2017-7-04', 'BlogPost 3', 2, null, 'www.speedment.com'),
        (11, 'BlogPost', '2018-7-04', 'BlogPost 4', 2, null, 'www.speedment.com'),
        (12, 'BlogPost', '2019-7-04', 'BlogPost 5', 2, null, 'www.speedment.com');

INSERT INTO author (id, firstname, lastname, version)
VALUES  (1, 'Author 1', 'Lastname', 2),
        (2, 'Author 2', 'Blog Post 2', 2),
        (3, 'Author 3', 'Blog Post 3', 2),
        (4, 'Author 5', 'Blog Post 4', 2),
        (5, 'Author 6', 'Blog Post 5', 2);

INSERT INTO publication_author(publication_id, author_id)
VALUES  (1, 1),
        (2, 2),
        (3, 3),
        (4, 4),
        (5, 5),
        (6, 1),
        (7, 2),
        (8, 3),
        (9, 4),
        (10, 5),
        (11, 1),
        (12, 2);
