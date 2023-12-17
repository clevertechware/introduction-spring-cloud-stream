# Introduction Spring Cloud Stream

This project is an example of a Spring Cloud Stream application.

## Getting Started

```bash
git clone https://github.com/clevertechware/introduction-spring-cloud-stream.git
cd introduction-spring-cloud-stream
./gradlew bootRun
```

Then you can send a message to the application using the following command:

```bash
curl -X POST --location "http://localhost:8080/foo/message" \
    -H "Content-Type: application/json" \
    -d '{
          "content": "Hello World",
          "receiver": "bar"
        }'
```

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.0/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.0/gradle-plugin/reference/html/#build-image)
* [GraalVM Native Image Support](https://docs.spring.io/spring-boot/docs/3.2.0/reference/html/native-image.html#native-image)
* [Cloud Stream](https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#spring-cloud-stream-overview-introducing)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#using.devtools)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#messaging.kafka)
* [Docker Compose Support](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#features.docker-compose)
* [Spring for RabbitMQ](https://docs.spring.io/spring-boot/docs/3.2.0/reference/htmlsingle/index.html#messaging.amqp)

### Guides

The following guides illustrate how to use some features concretely:

* [Messaging with RabbitMQ](https://spring.io/guides/gs/messaging-rabbitmq/)

### Additional Links

These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [Configure AOT settings in Build Plugin](https://docs.spring.io/spring-boot/docs/3.2.0/gradle-plugin/reference/htmlsingle/#aot)

### Docker Compose support

This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* rabbitmq: [`rabbitmq:latest`](https://hub.docker.com/_/rabbitmq)

Please review the tags of the used images and set them to the same as you're running in production.

## GraalVM Native Support

This project has been configured to let you generate either a lightweight container or a native executable.
It is also possible to run your tests in a native image.

### Lightweight Container with Cloud Native Buildpacks

If you're already familiar with Spring Boot container images support, this is the easiest way to get started.
Docker should be installed and configured on your machine prior to creating the image.

To create the image, run the following goal:

```
$ ./gradlew bootBuildImage
```

Then, you can run the app like any other container:

```
$ docker run --rm introduction-spring-cloud-stream:0.0.1-SNAPSHOT
```

### Executable with Native Build Tools

Use this option if you want to explore more options such as running your tests in a native image.
The GraalVM `native-image` compiler should be installed and configured on your machine.

NOTE: GraalVM 22.3+ is required.

To create the executable, run the following goal:

```
$ ./gradlew nativeCompile
```

Then, you can run the app as follows:

```
$ build/native/nativeCompile/introduction-spring-cloud-stream
```

You can also run your existing tests suite in a native image.
This is an efficient way to validate the compatibility of your application.

To run your existing tests in a native image, run the following goal:

```
$ ./gradlew nativeTest
```

