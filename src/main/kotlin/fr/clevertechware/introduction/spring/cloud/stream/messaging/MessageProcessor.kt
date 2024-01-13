package fr.clevertechware.introduction.spring.cloud.stream.messaging

import fr.clevertechware.introduction.spring.cloud.stream.Result
import fr.clevertechware.messages.avro.Message
import org.apache.avro.io.Decoder
import org.apache.avro.io.DecoderFactory
import org.apache.avro.specific.SpecificDatumReader

class MessageProcessor {
    private val decoderFactory = DecoderFactory.get()
    private val specificDatumReader = SpecificDatumReader(Message::class.java)

    fun process(jsonAvroMessage: String): Result? {
        LOGGER.debug("Receiving json avro message: {}", jsonAvroMessage)
        val message = deserialize(jsonAvroMessage)
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

    private fun deserialize(jsonAvroMessage: String): Message {
        val decoder: Decoder = decoderFactory.jsonDecoder(Message.getClassSchema(), jsonAvroMessage)
        return specificDatumReader.read(null, decoder)
    }

    private companion object {
        private val LOGGER = org.slf4j.LoggerFactory.getLogger(MessageProcessor::class.java)
    }
}