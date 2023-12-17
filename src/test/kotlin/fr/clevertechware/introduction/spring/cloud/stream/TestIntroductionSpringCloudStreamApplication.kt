package fr.clevertechware.introduction.spring.cloud.stream

import fr.clevertechware.introduction.spring.cloud.stream.testcontainers.SchemaRegistryContainer
import org.springframework.boot.fromApplication
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName


fun main(args: Array<String>) {
    val network: Network = Network.newNetwork()
    val kafka = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.2"))
        .withNetwork(network)
        .withNetworkAliases("kafka")
    kafka.start()
    kafka.waitingFor(Wait.forSuccessfulCommand("kafka-topics --bootstrap-server ${kafka.bootstrapServers} --create --list"))

    val schemaRegistry = SchemaRegistryContainer(kafka, DockerImageName.parse("confluentinc/cp-schema-registry:7.5.2"))
        .withNetwork(network)
        .withEnv("SCHEMA_REGISTRY_HOST_NAME", "testcontainer-schema-registry")
        .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "PLAINTEXT://kafka:9092")
    schemaRegistry.start()
    schemaRegistry.waitingFor(Wait.forHttp("/subjects").forStatusCode(200))

    val rabbit = RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"))
    rabbit.start()

    System.setProperty("spring.cloud.stream.kafka.binder.brokers", kafka.bootstrapServers)
    System.setProperty("schema.registry.url", "http://${schemaRegistry.host}:${schemaRegistry.port}")
    System.setProperty("spring.rabbitmq.addresses", rabbit.amqpUrl)
    System.setProperty("spring.rabbitmq.username", rabbit.adminUsername)
    System.setProperty("spring.rabbitmq.password", rabbit.adminPassword)
    System.setProperty("spring.profiles.active", "testcontainers")

    fromApplication<IntroductionSpringCloudStreamApplication>().run(*args)
}