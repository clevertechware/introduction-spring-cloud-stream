package fr.clevertechware.introduction.spring.cloud.stream

import fr.clevertechware.introduction.spring.cloud.stream.messaging.MessageProcessor
import fr.clevertechware.introduction.spring.cloud.stream.messaging.MessageProducer
import fr.clevertechware.introduction.spring.cloud.stream.messaging.ResultConsumer
import fr.clevertechware.messages.avro.Message
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.function.StreamOperations
import org.springframework.context.annotation.Bean
import java.time.Clock
import java.util.function.Consumer

@SpringBootApplication
class IntroductionSpringCloudStreamApplication {

    @Bean
    fun utcClock(): Clock = Clock.systemUTC()

    @Bean
    fun messageProcessor(): MessageProcessor = MessageProcessor()

    @Bean
    fun messageProducer(streamOperations: StreamOperations): MessageProducer = MessageProducer(streamOperations)

    @Bean
    fun resultConsumer(): ResultConsumer = ResultConsumer()

    @Bean
    fun processor(messageProcessor: MessageProcessor): (Message) -> Result? = messageProcessor::process

    @Bean
    fun consumer(resultConsumer: ResultConsumer): Consumer<Result> = Consumer(resultConsumer::consume)
}

fun main(args: Array<String>) {
    runApplication<IntroductionSpringCloudStreamApplication>(*args)
}
