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

import de.flapdoodle.embed.mongo.MongodExecutable;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Michael J. Simons, 2016-12-08
 */
@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedTestMongod(enabled = false)
public class AutoConfigureEmbeddedMongodDisabledIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testContextLoads() throws Exception {

        assertThat(this.context).isNotNull();
        assertThat(this.context.getBeanNamesForType(MongodExecutable.class)).isEmpty();
    }

    @TestConfiguration
    static class Config {

    }
}
