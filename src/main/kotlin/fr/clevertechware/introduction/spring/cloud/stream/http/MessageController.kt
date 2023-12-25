package fr.clevertechware.introduction.spring.cloud.stream.http

import fr.clevertechware.introduction.spring.cloud.stream.messaging.MessageProducer
import fr.clevertechware.messages.avro.Message
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import java.time.Clock
import java.util.function.Supplier

@RestController
@Tag(name = "Messages endpoint")
class MessageController(
    private val idGenerator: Supplier<String>,
    private val messageProducer: MessageProducer,
    private val clock: Clock
) {

    @ResponseStatus(code = NO_CONTENT)
    @PostMapping("/{sender}/message")
    @Operation(summary = "Send a message to a receiver")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Message sent"),
            ApiResponse(responseCode = "400", description = "Invalid body supplied"),
            ApiResponse(responseCode = "404", description = "Book not found")
        ]
    )
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