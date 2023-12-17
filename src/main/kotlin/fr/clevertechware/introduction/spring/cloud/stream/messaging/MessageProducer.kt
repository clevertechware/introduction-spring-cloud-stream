package fr.clevertechware.introduction.spring.cloud.stream.messaging

import fr.clevertechware.messages.avro.Message
import org.springframework.cloud.stream.function.StreamOperations

class MessageProducer(private val streamOperations: StreamOperations) {
    fun produce(message: Message) {
        LOGGER.info("Producing message: {}", message)
        streamOperations.send("producer", message)
    }

    private companion object {
        private val LOGGER = org.slf4j.LoggerFactory.getLogger(MessageProducer::class.java)
    }
}