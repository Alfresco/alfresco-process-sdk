# Welcome to the Alfresco Process SDK

<p>
  <img title="alfresco" alt='alfresco' src='docs/images/alfresco.png'  width="229px" height="160px"/>
</p>

## What is Alfresco Process SDK?

The Alfresco Process SDK includes APIs and samples that allows developers to quickly build Java applications that integrate with APA. <br/>

This SDK provides functionality to connect to Cloud-based servers. APA version 7.x and above are supported.

## How does it work?

The Alfresco Process SDK consists of:

[alfresco-apa-java-rest-api](alfresco-apa-java-rest-api): Allows applications to consume APA public REST APIs.

The [samples](samples) folder includes examples, sample applications and code snippets of the different features supported by the SDK. Each sample application
contains a `docker-compose` file and scripts that allows you to build and run the extension.

### Pre-Requisites

* Java version 11 or higher
* Maven version 3.3 or higher

### Quick start

#### 1. Create a new Spring Boot application

#### 2. Add these dependencies to your project's build file:

**Maven**

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

**Gradle**

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

#### 3. Configure REST API

In your ```application.properties``` file provide URL, authentication mechanism and credentials for accessing the REST API:

```
content.service.url=http://repository:8080
content.service.security.basicAuth.username=admin
content.service.security.basicAuth.password=admin
```

If you are using OAuth2, you can use client-credential based authentication:

```
spring.security.oauth2.client.registration.alfresco-rest-api.provider=alfresco-identity-service
spring.security.oauth2.client.registration.alfresco-rest-api.client-id=clientId
spring.security.oauth2.client.registration.alfresco-rest-api.client-secret=clientSecret
spring.security.oauth2.client.registration.alfresco-rest-api.authorization-grant-type=client_credentials
spring.security.oauth2.client.provider.alfresco-identity-service.token-uri=${keycloak.auth-server-url}/auth/realms/${keycloak.realm}/protocol/openid-connect/token
```

Or OAuth2 password based authentication:

```
spring.security.oauth2.client.registration.alfresco-rest-api.provider=alfresco-identity-service
spring.security.oauth2.client.registration.alfresco-rest-api.client-id=clientId
spring.security.oauth2.client.registration.alfresco-rest-api.client-secret=clientSecret
spring.security.oauth2.client.registration.alfresco-rest-api.username=username
spring.security.oauth2.client.registration.alfresco-rest-api.password=pwd
spring.security.oauth2.client.registration.alfresco-rest-api.authorization-grant-type=password
spring.security.oauth2.client.provider.alfresco-identity-service.token-uri=${keycloak.auth-server-url}/auth/realms/${keycloak.realm}/protocol/openid-connect/token
```

Finally, if you want to provide a custom authentication mechanism, you can enable the delegated external authentication:

```
content.service.security.delegated=true
```

And provide a bean that implements the interface ```DelegatedAuthenticationProvider```.

#### 5. Consume the REST API

TODO: Add code example or correct the link

You can find more information about how to consume the REST API at [alfresco-java-rest-api](alfresco-apa-java-rest-api).

## How to build the SDK project

### Pre-Requisites

* Java version 11 or higher
* Maven version 3.3 or higher
* An APA environment with a deployed application from which to fetch the API definitions

### Build command

Simply run the next command:

```console
mvn clean install -Denvironment.host=your-installed-environment-host -Denvironment.apa.host=your-installed-apa-environment-host -Denvironment.application.name=your-deployed-APA-application-name
```

The previous command will generate the source code for the APA REST API clients first, then it will compile all the modules and run the
corresponding unit and integration tests.

If you want to skip the generation of the REST API clients code, you can do it sending the maven property `skip.generation`:

```console
mvn clean install -Dskip.generation
```

Remember that you need to build the project at least once without skipping the generation step to be able to compile all the code properly. From that point, you
can skip the generation step if unneeded.
