# IMPORTANT 

You don't need this for Spring Boot >= 1.5.x, it's included now in Spring Boot itself, see:
[41.3.10 Auto-configured Data MongoDB tests](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-mongo-test)

-------


# datamongotest-autoconfigure-spring-boot

Provides a `@DataMongoTest` for the automatic configuration of tests with Spring Boot 1.4+.

*Note*: I have proposed this as a PR for Spring Boot itself: [Provide a @DataMongoTest similar to @DataJpaTest](https://github.com/spring-projects/spring-boot/pull/7600)

## Introduction

Spring Boot 1.4 brought a new feature called [Auto-configured tests](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-1.4-Release-Notes#auto-configured-tests). 

Thoses "slices" can be used when starting a full application auto-configuration is overkill for a specific tests.

The reference documentation has [chapter](http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-autoconfigured-tests) on how to use them. 

[Stéphane Nicoll](https://twitter.com/snicoll) created a nice post on how to create your own slice, called [Custom test slice with Spring Boot 1.4](https://spring.io/blog/2016/08/30/custom-test-slice-with-spring-boot-1-4). 

`@DataMongoTest` uses his work to provide a custom test slice that works with pretty much the same way for [Spring Data MongoDB](http://projects.spring.io/spring-data-mongodb/) as `@DataJpaTest` does for Spring Data JPA.

## How to use it

Add 

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
 <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>eu.michael-simons</groupId>
    <artifactId>datamongotest-autoconfigure-spring-boot</artifactId>
    <version>0.0.1</version>
    <scope>test</scope>
</dependency>
```

to your dependencies, create and write your MongoDB repositories in your Spring Boot Application as you did before.

Annotate your test with `@DataMongoTest` and benefit from a `MongoTemplate` and all your repositories:

```java
@RunWith(SpringRunner.class)
@DataMongoTest
public class DataMongoSampleTests {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private TweetRepository tweetRepository;

    @Test
    public void testStuff() {
        TweetDocument tweet = new TweetDocument();
        tweet.setText("Look, new @DataMongoTest!");
        
        tweet = this.tweetRepository.save(tweet);
        assertThat(tweet.getId(), notNullValue());
        
        assertTrue(this.mongoTemplate.collectionExists("tweets"));
    }
}
```

The automatic configuration takes your `application.properties` into account. Make sure you configure another database connection for your test profile through

```
spring.data.mongodb.database = testdatabase
```

Or you might consider adding 

```xml
<dependency>
    <groupId>de.flapdoodle.embed</groupId>
    <artifactId>de.flapdoodle.embed.mongo</artifactId>
    <scope>test</scope>
</dependency>
```

to your test dependencies. This activates Spring Boot support for an embedded MongoDB process and you end up with an embedded Mongo connection like you do when using `@DataJpaTest` where you get an H2 embedded database.

If you don't want this and but have embedded Mongo on your path, consider adding

```java
@AutoConfigureEmbeddedTestMongod(enabled = false)
```

to your test as well.
