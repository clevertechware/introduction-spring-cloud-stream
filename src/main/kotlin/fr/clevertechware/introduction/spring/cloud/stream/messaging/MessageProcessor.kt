package fr.clevertechware.introduction.spring.cloud.stream.messaging

import fr.clevertechware.introduction.spring.cloud.stream.Result
import fr.clevertechware.messages.avro.Message

class MessageProcessor {
    fun process(message: Message): Result? {
        LOGGER.info("Processing messages: {}", message)
        val hasNoContent = message.content.isNullOrBlank()
        if (hasNoContent) {
            LOGGER.debug("Message {} is filter out because it does not have any content", message.id)
            return null
        }
        val hasNoReceiver = message.receiver.isNullOrBlank()
        if (hasNoReceiver) {
            LOGGER.debug("Message {} is filter out because it does not have any receiver", message.id)
            return null
        }
        return Result(
            id = message.id,
            timestamp = message.timestamp,
            content = message.content,
            sender = message.sender,
            receiver = message.receiver
        )
    }

    private companion object {
        private val LOGGER = org.slf4j.LoggerFactory.getLogger(MessageProcessor::class.java)
    }
}