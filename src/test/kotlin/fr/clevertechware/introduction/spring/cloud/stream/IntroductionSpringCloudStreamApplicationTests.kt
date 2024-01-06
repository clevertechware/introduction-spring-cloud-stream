package fr.clevertechware.introduction.spring.cloud.stream

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Clock
import java.util.*
import java.util.function.Supplier


@AutoConfigureMockMvc
@SpringBootTest(properties = ["spring.main.allow-bean-definition-overriding=true"])
class IntroductionSpringCloudStreamApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var store: Queue<Result>

    @Test
    fun `should consume message with content from sender to receiver`() {
        this.mockMvc.perform {
            post("/sender/message")
                .contentType("application/json")
                .content(
                    """
    {
      "content": "Hello World!",
      "receiver": "receiver"
    }
                    """.trimIndent()
                )
                .buildRequest(it)
        }.andExpect { status().isOk }


        assertThat(this.store.peek()).isEqualTo(
            Result(
                id = "id",
                timestamp = 1609459200000,
                content = "Hello World!",
                sender = "sender",
                receiver = "receiver"
            )
        )
    }

    @TestConfiguration
    class TestConfig {

        @Bean
        fun utcClock(): Clock = Clock.fixed(
            java.time.Instant.parse("2021-01-01T00:00:00.00Z"),
            java.time.ZoneId.of("UTC")
        )

        @Bean
        fun idGenerator(): Supplier<String> = Supplier { "id" }
    }
}
