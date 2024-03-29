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

package org.alfresco.rest.sdk.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableFeignClients(basePackages = {
    "org.alfresco.activiti.deployment.handler",
    "org.alfresco.activiti.modeling.handler",
    "org.alfresco.activiti.process.storage.handler",
    "org.alfresco.activiti.query.handler",
    "org.alfresco.activiti.audit.handler",
    "org.alfresco.activiti.form.handler",
    "org.alfresco.activiti.preference.handler",
    "org.alfresco.activiti.script.modeling.handler",
    "org.alfresco.activiti.dmn.simulation.handler",
    "org.alfresco.activiti.runtime.handler"
})
@PropertySource("classpath:config/alfresco-apa-rest-api.properties")
public class AlfrescoAPARestApiAutoConfiguration {

}
