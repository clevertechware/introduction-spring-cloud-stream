package fr.clevertechware.introduction.spring.cloud.stream.testcontainers

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName

class SchemaRegistryContainer(kafka: KafkaContainer, dockerImageName: DockerImageName) :
    GenericContainer<SchemaRegistryContainer>(dockerImageName) {

    init {
        withExposedPorts(8081)
    }


    val port: Int
        get() = getMappedPort(8081)!!

}