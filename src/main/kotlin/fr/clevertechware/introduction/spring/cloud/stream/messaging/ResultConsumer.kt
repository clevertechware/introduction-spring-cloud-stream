package fr.clevertechware.introduction.spring.cloud.stream.messaging

import fr.clevertechware.introduction.spring.cloud.stream.Result
import org.slf4j.LoggerFactory.getLogger

class ResultConsumer {
    fun consume(result: Result) {
        LOGGER.info("Consuming result: {}", result)
    }

    private companion object {
        private val LOGGER = getLogger(ResultConsumer::class.java)
    }
}