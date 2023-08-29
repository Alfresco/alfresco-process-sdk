/*
 * Copyright 2021-2023 Alfresco Software, Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.alfresco.rest.sdk;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class APAApplicationTest {

    @Autowired
    private Environment env;

   @Test
    void contextLoads() {
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "modeling.url",
        "modeling.path",
        "alfresco.service.deployment.url",
        "alfresco.service.deployment.path",
        "activiti.service.query.url",
        "activiti.service.query.path",
        "activiti.service.audit.url",
        "activiti.service.audit.path",
        "activiti.service.runtime.url",
        "activiti.service.runtime.path",
        "activiti.service.form.url",
        "activiti.service.form.path",
        "activiti.service.preference.url",
        "activiti.service.preference.path",
        "alfresco.service.dmn.simulation.url",
        "alfresco.service.dmn.simulation.path",
        "alfresco.service.script.modeling.url",
        "alfresco.service.script.modeling.path",
    })
    void configurationPropertiesExist(String propertyName) {
        assertThat(env.getProperty(propertyName)).isNotBlank();
    }
}
