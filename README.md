<img align="center" src="https://github.com/speedment/speedment-resources/blob/master/src/main/resources/logo/JPAstreamer-beta.png?raw=true." alt="JPA Streamer Logo" title="JPA Streamer Logo" width="600px">

# JPAstreamer

[![Join the chat at https://gitter.im/speedment/jpa-streamer](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/speedment/jpa-streamer?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

JPAstreamer is a lightweight extension for any JPA provider that allows creation of Java Streams from database content. With a single dependency, your application can immediately operate on database elements using standard Stream operators e.g. filter(), sort() and map().       

The following example assumes there is an existing JPA Entity that represents a table containing films as follows: 

    @Entity
    @Table(name = "film", schema = "sakila")
    public class Film implements Serializable {

   	    @Id
   	    @GeneratedValue(strategy = GenerationType.IDENTITY)
   	    @Column(name = "film_id", nullable = false, updatable = false, columnDefinition = "smallint(5)")
   	    private Integer filmId;

  	    @Column(name = "title", nullable = false, columnDefinition = "varchar(255)")
        private String title;

        @Column(name = "length", columnDefinition = "smallint(5)")
        private Integer length;

        @Column(name = "rating", columnDefinition = "enum('G','PG','PG-13','R','NC-17')")
        private String rating;
        
    }
    
To operate on the elements of the table, JPAstreamer is first initialized with a simple builder (in this case using a persistence unit named "sakila"):

    JPAStreamer jpaStreamer = JPAStreamer.createJPAStreamerBuilder("sakila")
        .build();

The obtained streamer is then used to create Streams that are rendered to database queries through JPA. For example:

    jpaStreamer.stream(Film.class) // Film.class is the @Entity representing the film-table
        .filter(Film$.rating.equal("G"))    
        .sorted(Film$.length.reversed().thenComparing(Film$.title.comparator()))
        .skip(10)
        .limit(5)
        .forEach(System.out::println);

This will print films rated G in reversed length order (where films of equal length will be in title order) but skipping the first ten and then printing only the following five films. (Film$ is automatically generated from the Film-table Entity at compile-time by JPAstreamer). 

More examples are available in the JPAstreamer [documentation](https://speedment.github.io/jpa-streamer/jpa-streamer/0.1.0/fetching-data/stream-examples.html). 

## Install
Since JPAstreamer acts merely as an API extension for existing JPA providers it requires minimal installation and configuration efforts. You only need to specify that the JPAstreamer dependency is required to compile your source code. 

> **_NOTE:_** JPAStreamer requires use of Java 8 or later.

### Maven
In Maven, the following JPAstreamer dependency is added to the project's pom.xml-file.

    <dependencies>
	    <dependency>
            <groupId>com.speedment.jpastreamer</groupId>
            <artifactId>core</artifactId>
            <version>${jpa-streamer-version}</version>
        </dependency>
    </dependencies>

### Gradle
In Gradle, the following JPAstreamer dependency is added to the project's build.gradle-file:

    repositories {
	    mavenCentral()
    }
    dependencies {
        compile "com.speedment.jpastreamer:core:version"
        annotationProcessor "com.speedment.jpastreamer:fieldgenerator-standard:version"
    }

## Resources

- **Documentation** - https://speedment.github.io/jpa-streamer
- **Gitter Chat** - https://gitter.im/speedment/jpa-streamer
- **Website** - www.jpastreamer.org

## Contributing
We gladly welcome any form of contributions, whether it is comments and questions, filed issues or pull requests. 

Before we can accept your patches we need to establish a common legal ground to protect your rights to your contributions and the users of JPAstreamer. This is done by signing a Contributor License Agreement (CLA) with Speedment, Inc. The details of this process is laid out [here](). 
 
## Phone Home
JPAstreamer sends certain data back to Speedment's servers as described [here](https://github.com/speedment/jpa-streamer/blob/master/DISCLAIMER.MD). If you wish to disable this feature, please contact us at info@jpastreamer.org.

## License
JPAstreamer is released under the [LGPL 2.1 License](https://github.com/speedment/jpa-streamer/blob/master/LICENSE). 
