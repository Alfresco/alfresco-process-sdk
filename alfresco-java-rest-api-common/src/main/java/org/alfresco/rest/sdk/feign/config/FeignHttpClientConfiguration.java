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

package org.alfresco.rest.sdk.feign.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for Feign OkHttp Client
 */
@Configuration
public class FeignHttpClientConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(feign.okhttp.OkHttpClient.class)
    @ConditionalOnBean(okhttp3.OkHttpClient.class)
    static class FeignOkHttpClientConfiguration {

        @Bean
        public feign.Client feignClient(okhttp3.OkHttpClient client) {
            return new feign.okhttp.OkHttpClient(client);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(feign.hc5.ApacheHttp5Client.class)
    @ConditionalOnBean(HttpClient.class)
    static class FeignApache5ClientHttpClientConfiguration {

        @Bean
        public feign.Client feignClient(HttpClient client) {
            return new feign.hc5.ApacheHttp5Client(client);
        }
    }
}
