/*
 * Copyright 2016 michael-simons.eu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ac.simons.spring.boot.test.autoconfigure.data.mongo;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Sample test for {@link DataMongoTest @DataMongoTest}
 *
 * @author Michael J. Simons
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("it")
@DataMongoTest
@AutoConfigureEmbeddedTestMongod(enabled = false)
public class DataMongoTestSampleIT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TweetRepository tweetRepository;
    
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testStuff() {
        TweetDocument tweet = new TweetDocument();
        tweet.setText("Look, new @DataMongoTest!");

        tweet = this.tweetRepository.save(tweet);
        assertThat(tweet.getId(), notNullValue());

        assertTrue(this.mongoTemplate.collectionExists("tweets"));
    }

    @Test
    public void didNotInjectExampleController() {
        this.thrown.expect(NoSuchBeanDefinitionException.class);
        this.applicationContext.getBean(ExampleService.class);
    }

    @Configuration
    static class Config {
    }
}
