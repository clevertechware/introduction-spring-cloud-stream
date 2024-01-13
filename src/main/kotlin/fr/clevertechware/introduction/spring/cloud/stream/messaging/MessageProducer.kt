package fr.clevertechware.introduction.spring.cloud.stream.messaging

import fr.clevertechware.messages.avro.Message
import org.apache.avro.io.EncoderFactory
import org.apache.avro.specific.SpecificDatumWriter
import org.springframework.cloud.stream.function.StreamOperations
import java.io.ByteArrayOutputStream

class MessageProducer(private val streamOperations: StreamOperations) {
    private val encoderFactory = EncoderFactory.get()

    fun produce(message: Message) {
        LOGGER.info("Producing message: {}", message)
        val jsonAvroMessage = serialize(message)
        streamOperations.send("producer", jsonAvroMessage)
    }

    private fun serialize(message: Message): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val jsonEncoder = encoderFactory.jsonEncoder(message.schema, byteArrayOutputStream)
        val writer = SpecificDatumWriter(Message::class.java)
        writer.write(message, jsonEncoder)
        jsonEncoder.flush()
        val jsonAvro = byteArrayOutputStream.toString()
        LOGGER.debug("Serialized {} as application/json+avro : {}", jsonAvro, jsonAvro)
        return jsonAvro
    }

    private companion object {
        private val LOGGER = org.slf4j.LoggerFactory.getLogger(MessageProducer::class.java)
    }
}