# Welcome to the Alfresco Process SDK

![Build Status](https://github.com/Alfresco/alfresco-process-sdk/actions/workflows/build.yml/badge.svg)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/335dc8e0ffc54eada8d4ac674131b666)](https://www.codacy.com?utm_source=github.com&utm_medium=referral&utm_content=Alfresco/alfresco-process-sdk&utm_campaign=Badge_Grade)

![alfreso logo](./docs/images/alfresco.png)

## What is Alfresco Process SDK?

The Alfresco Process SDK includes APIs and samples that allows developers to quickly build Java applications that integrate with APA.

This SDK provides functionality to connect to Cloud-based servers. APA version 7.x and above are supported.

The APIs contained within have been generated using [Swagger Codegen](https://swagger.io/tools/swagger-codegen) from the public API definitions.

## What is included?

The Alfresco Process SDK consists of these parts:

- The main API ([alfresco-apa-java-rest-api](alfresco-apa-java-rest-api)) allows applications to consume APA public REST APIs.
- The [samples](samples) folder includes a sample application configured to use the SDK. This has a `docker-compose` file and scripts that allows you to build and run the application. See [README](samples/java-rest-api-clients/README.md) for more details.
- [Common logic](alfresco-java-rest-api-common) for REST APIs (primarily for authentication)

## Getting Started

### Usage Notes

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

### Quick start

This section contains the steps required for using the SDK with Spring.

#### Pre-Requisites

- Java version 17 or higher
- Maven version 3.3 or higher

#### 1. Create a new Spring Boot application

#### 2. Add these dependencies to your project's build file

##### Maven

First, add to the repositories the Alfresco public repository containing the artifacts:

```xml

<repositories>

  <repository>
    <id>alfresco-public</id>
    <url>https://artifacts.alfresco.com/nexus/content/groups/public</url>
  </repository>

</repositories>
```

Then, add the dependency on the APA starter

```xml

<dependencies>

  <!-- APA Java REST APIs -->
  <dependency>
    <groupId>org.alfresco</groupId>
    <artifactId>alfresco-apa-java-rest-api-spring-boot-starter</artifactId>
    <version>{version-number}</version>
  </dependency>

</dependencies>
```

##### Gradle

First, add to the repositories the Alfresco public repository containing the artifacts:

```groovy
repositories {
  maven {
    url "https://artifacts.alfresco.com/nexus/content/groups/public"
  }
}
```

Then, add the dependency on the starter

```groovy
compile "org.alfresco:alfresco-apa-java-rest-api-spring-boot-starter:{version-number}"
```

#### 3. Configure Authentication for the REST API

In your `application.yml` file you can configure settings related to the content service repository, authentication mechanism, and credentials for accessing the REST API:

```yaml
content:
  service:
    url: https://repository:8080
    security:
      basicAuth:
        username: username-here
        password: password-here
```

If you are using OAuth2, you can use client-credential based authentication:

```yaml
spring
  security:
    oauth2:
      client:
        registration:
          alfresco-rest-api:
            provider: alfresco-identity-service
            client-id: clientId
            client-secret: clientSecret
            authorization-grant-type: client_credentials
        provider:
          alfresco-identity-service:
            token-uri: ${keycloak.auth-server-url}/auth/realms/${keycloak.realm}/protocol/openid-connect/token
```

Or OAuth2 password based authentication:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          alfresco-rest-api:
            provider: alfresco-identity-service
            client-id: clientId
            client-secret: clientSecret
            username: username-here
            password: password-here
            authorization-grant-type: password
        provider:
          alfresco-identity-service:
            token-uri: ${keycloak.auth-server-url}/auth/realms/${keycloak.realm}/protocol/openid-connect/token
```

Additionally, if you want to provide a custom authentication mechanism, you can enable the delegated external authentication:

```yaml
content:
  service:
    security:
      delegated: true
```

And provide a bean that implements the interface `DelegatedAuthenticationProvider`.

#### 4. Configure Other Settings

Here are some settings you may need to configure in your Application configuration file before using the SDK

```yaml
keycloak:
  auth-server-url: http://example.com/auth}
  principal-attribute: ${ACT_KEYCLOAK_PRINCIPAL_ATTRIBUTE:preferred-username}
  public-client: ${ACT_KEYCLOAK_CLIENT:true}
  realm: ${ACT_KEYCLOAK_REALM:springboot}
  resource: ${ACT_KEYCLOAK_RESOURCE:activiti}
  security-constraints:
    - authRoles:
        - ${ACT_KEYCLOAK_ROLES:user}
      securityCollections:
        - patterns:
            - ${ACT_KEYCLOAK_PATTERNS:/v1/*}
    - authRoles:
        - ${admin-role-name}
      securityCollections:
        - patterns:
            - /admin/*
  ssl-required: ${ACT_KEYCLOAK_SSL_REQUIRED:none}

activiti:
  service:
    query:
      url: https://example.com
      path: /example-app/query
    runtime:
      url: https://example.com
      path: /example-app/rb
    form:
      url: https://example.com
      path: /example-app/form
    audit:
      url: https://example.com
      path: /example-app/audit
    deployment:
      url: https://example.com
      path: /deployment-service
    modeling:
      url: https://example.com
      path: /modeling-service
    process-storage:
      url: https://example.com
      path: /process-storage

authentication:
  service:
    url: ${AUTHENTICATION_RUNTIME_URL:http://example.com}
    path: ${AUTHENTICATION_PATH:/alfresco/api/-default-/public/alfresco/versions/1}

process:
  service:
    url: ${PROCESS_SERVICE_URL:http://example.com}
    path: ${PROCESS_SERVICE_PATH:/alfresco/api/-default-/public/alfresco/versions/1}
```

#### 4. Consume the REST API

- Add to your main Spring application

  ```java
  @EnableFeignClients(basePackages = {"com.alfresco.core.handler",
                                      "com.alfresco.activiti.handler",
                                      "com.alfresco.search.handler",
                                      "com.alfresco.auth.handler"})
  public class SampleApplication { }

  ```

- Copy the class in the folder config/FeignConfiguration.java in your project
  In order to resolve the references in FeignConfiguration you will need the following `<dependencies>`

  ```xml
  <dependencies>
    <dependency>
        <groupId>org.activiti.cloud</groupId>
        <artifactId>activiti-cloud-api-process-model-impl</artifactId>
        <version>version-here</version>
    </dependency>
    <dependency>
        <groupId>org.activiti.cloud</groupId>
        <artifactId>activiti-cloud-api-task-model-impl</artifactId>
        <version>version-here</version>
    </dependency>
    <dependency>
        <groupId>org.activiti</groupId>
        <artifactId>activiti-api-runtime-shared</artifactId>
        <version>version-here</version>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.datatype</groupId>
        <artifactId>jackson-datatype-jsr310</artifactId>
    </dependency>
  </dependencies>
  ```

- If you are using keycloak you must include also the following:

  ```xml
    <dependencies>
      <dependency>
        <groupId>org.activiti.cloud</groupId>
        <artifactId>activiti-cloud-services-common-security</artifactId>
        <version>version-here</version>
      </dependency>
    </dependencies>
  ```

To see how classes within the SDK can be used to call APIs, refer to [this example](samples/java-rest-api-clients/src/main/java/org/alfresco/java/rest/client/sample/service/RESTClientService.java)

### Usage in a non-spring application

You can check the documentation here:
<https://github.com/OpenFeign/feign>

## Building the SDK

It is not necessary to build the SDK in order to use it. You can instead reference the released version of the sdk
by following the instructions [above](#getting-started).

If you still intend to build it, follow these instructions:

### Pre-Requisites

- Java version 17 or higher
- Maven version 3.3 or higher
- An APA environment with a deployed application from which to fetch the API definitions

### Build Steps

In order to build the SDK for the first time you will need access to a running APA environment.
This is necessary in order to retrieve the API definitions.

You can either set the necessary environment variables ahead of time, or include them with the build command.

Setting in advance:

```console
MAVEN_CLI_OPTS="${MAVEN_CLI_OPTS} -Denvironment.host=your-installed-environment-host"
MAVEN_CLI_OPTS="${MAVEN_CLI_OPTS} -Denvironment.apa.host=your-installed-apa-environment-host"
MAVEN_CLI_OPTS="${MAVEN_CLI_OPTS} -Denvironment.application.name=your-deployed-APA-application-name"
MAVEN_CLI_OPTS="${MAVEN_CLI_OPTS} -U"
export MAVEN_CLI_OPTS
```

Included on command line:

```console
mvn clean install -Denvironment.host=your-installed-environment-host -Denvironment.apa.host=your-installed-apa-environment-host -Denvironment.application.name=your-deployed-APA-application-name
```

This will generate the source code for the APA REST API clients first, then it will compile all the modules and run the
corresponding unit and integration tests.

If you want to skip the generation of the REST API clients code, you can do so by setting the maven property `skip.generation`:

```console
mvn clean install -Dskip.generation
```

However, you must build the project at least once _without_ skipping the generation step in order to compile the SDK. From that point, you
can skip the generation step if unneeded.

## License Management

Modules in this repo use the following Maven plugins to perform licensing related tasks:

- [com.mycila:license-maven-plugin](https://mycila.carbou.me/license-maven-plugin/) is used for checking and updating the license header on all applicable files. This is typically done at the beginning of a new year.
- [org.codehaus.mojo:license-maven-plugin](https://www.mojohaus.org/license-maven-plugin/) is used to manage third party licenses. Specifically generating a report of all licenses used by dependencies.

Since these plugins have the same name (excluding the groupId), running them requires qualifiying the goal with the full name.

### Updating the license header

To check that the license header is consistent across all source files, run this command:

`mvn com.mycila:license-maven-plugin:check`

If any source files have a modified or missing license header, this goal will fail.

To fix the license header on all source files, run this command:

`mvn com.mycila:license-maven-plugin:format`

Any files that did not have the correct header will be reformatted and appear in your unstaged changes.

You can use this command to update the license header when the Copyright year should be updated. Before running the format goal, modify [pom.xml](pom.xml) with the current year:

```xml
<properties>
    <project.year>year-here</project.year>
</properties>
```

See the [plugin documentation](https://mycila.carbou.me/license-maven-plugin/) for all possible goals.

### Listing Third Party Licenses

To list all third party licenses across all modules, the following command can be used:

`mvn org.codehaus.mojo:license-maven-plugin:aggregate-third-party-report`

This will create a file located at `.\target\site\aggregate-third-party-report.html`, which contains a list of the licenses for all dependencies for all modules.

Additionally, per-module text-based reports can be created with this command:

`mvn org.codehaus.mojo:license-maven-plugin:add-third-party`

For each module a file will be created at `target\generated-sources\license\THIRD-PARTY.txt`

See the [plugin documentation](https://www.mojohaus.org/license-maven-plugin/) for all possible goals.

## CI/CD

Running on GH Actions.

For Dependabot PRs to be validated by CI, the label "CI" should be added to the PR.

Requires the following variables to be set:

| Name                 | Description                                                      |
| -------------------- | ---------------------------------------------------------------- |
| ENVIRONMENT_APA_HOST | URL of APA environment to use for SDK generation                 |
| ENVIRONMENT_APP      | Name of the app within the environment to use for SDK generation |
| ENVIRONMENT_HOST     | Base URL of environment to use for SDK generation                |

Requires the following secrets to be set:

| Name                         | Description                        |
| ---------------------------- | ---------------------------------- |
| BOT_GITHUB_TOKEN             | Token to launch other builds on GH |
| BOT_GITHUB_USERNAME          | Username to issue propagation PRs  |
| DOCKER_USERNAME              | Docker Hub repository username     |
| DOCKER_PASSWORD              | Docker Hub repository password     |
| NEXUS_USERNAME               | Internal Maven repository username |
| NEXUS_PASSWORD               | Internal Maven repository password |
| QUAY_USERNAME                | Quay.io docker registry username   |
| QUAY_PASSWORD                | Quay.io docker registry password   |
| SLACK_NOTIFICATION_BOT_TOKEN | Token to notify slack on failure   |


## Release process

The release process is partially automated using the [release workflow](.github/workflows/release.yml).
This workflow creates a release branch, updates the version in the `pom.xml` files, and creates a PR for the release branch.
When the PR is merged, the release is automatically published to the internal Maven repository.
