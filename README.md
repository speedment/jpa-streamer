<img align="center" src="https://github.com/speedment/speedment-resources/blob/master/src/main/resources/logo/JPAstreamer.png?raw=true." alt="JPA Streamer Logo" title="JPA Streamer Logo" width="900px">

# JPA Streamer

JPA Streamer is a lightweight toolkit for creating Java Streams from database content using any JPA provider such as Hibernate.

## Initializing JPA Streamer

This is how you initialize JPA Streamer using a persistence unit name "sakila":

    JPAStreamer jpaStreamer = JPAStreamer.createJPAStreamerBuilder("sakila")
                .build(); 

## Examples

Here are some examples of streams that can be rendered to database queries though JPA.

### Streaming

        jpaStreamer.stream(Film.class)
                .forEach(System.out::println);

This will print all films in the database.

### Filtering



        jpaStreamer.stream(Film.class)
                .filter(Film$.length.between(100, 120))
                .forEach(System.out::println);
                
This will print all films with a length between 100 and 120 (inclusive).

### Combined Filters

        jpaStreamer.stream(Film.class)
                // Composing filters with "and"/"or"
                .filter(Film$.rating.equal("G").or(Film$.length.greaterOrEqual(140)))
                .forEach(System.out::println);

This will print all films that are either rated "G" or has a length greater or equal to 140 minutes.                

### Sorting with skip and limit
                
        jpaStreamer.stream(Film.class)
                .filter(Film$.rating.equal("G"))
                .sorted(Film$.length.reversed().thenComparing(Film$.title.comparator()))
                .skip(10)
                .limit(5)
                .forEach(System.out::println);

This will print films rated G in reversed length order (where films of equal length will be in title order)
but skipping the first 10 and then printing only the following 5 films.


### Counting

        long count = jpaStreamer.stream(Film.class)
                .filter(Film$.rating.equal("G"))
                .count();

This will count the number of films with a rating of "G"

                
## Cleaning up

    jpaStreamer.close();

This will release any resources potentially held by JPA Streamer.    
