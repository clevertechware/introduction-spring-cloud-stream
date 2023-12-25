package fr.clevertechware.introduction.spring.cloud.stream.http

import fr.clevertechware.introduction.spring.cloud.stream.messaging.MessageProducer
import fr.clevertechware.messages.avro.Message
import jakarta.validation.Valid
import org.slf4j.LoggerFactory.getLogger
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Clock
import java.util.function.Supplier

@RestController
class MessageController(
    private val idGenerator: Supplier<String>,
    private val messageProducer: MessageProducer,
    private val clock: Clock
) {

    @PostMapping("/{sender}/message")
    fun postMessage(
        @PathVariable sender: String,
        @Valid @RequestBody messageRequestBody: MessageRequestBody
    ) {
        LOGGER.info("Requesting message: {}", messageRequestBody)
        val message = Message.newBuilder()
            .setId(idGenerator.get())
            .setTimestamp(clock.instant().toEpochMilli())
            .setContent(messageRequestBody.content)
            .setSender(sender)
            .setReceiver(messageRequestBody.receiver)
            .build()
        LOGGER.info("Sending message: {}", message)
        messageProducer.produce(message)
    }


    private companion object {
        private val LOGGER = getLogger(MessageController::class.java)
    }

}