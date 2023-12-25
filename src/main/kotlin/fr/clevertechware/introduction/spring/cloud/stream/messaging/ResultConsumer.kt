package fr.clevertechware.introduction.spring.cloud.stream.messaging

import fr.clevertechware.introduction.spring.cloud.stream.Result
import org.slf4j.LoggerFactory.getLogger
import java.util.Queue

class ResultConsumer(private val store: Queue<Result>) {
    fun consume(result: Result) {
        LOGGER.info("Consuming result: {}", result)
        store.add(result)
    }

    private companion object {
        private val LOGGER = getLogger(ResultConsumer::class.java)
    }
}