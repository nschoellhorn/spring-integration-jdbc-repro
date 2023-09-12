package de.kopf3.springintegrationjdbcrepro

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.jdbc.store.JdbcMessageStore
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.GenericMessage
import javax.sql.CommonDataSource
import javax.sql.DataSource

@SpringBootApplication
@EnableIntegration
class SpringIntegrationJdbcReproApplication {

    @Bean
    @Scope("prototype")
    fun jdbcMessageStore(dataSource: DataSource) = JdbcMessageStore(dataSource)

    @Bean
    fun runner(
        flowChannelA: MessageChannel,
        flowChannelB: MessageChannel,
    ): CommandLineRunner = CommandLineRunner {
        val messageX = GenericMessage<String>("X", mapOf(
            "someHeader" to "test",
            "otherHeader" to "x",
        ))
        val messageY = GenericMessage<String>("Y", mapOf(
            "someHeader" to "test",
            "otherHeader" to "y",
        ))
        val messageZ = GenericMessage<String>("Z", mapOf(
            "someHeader" to "test",
            "otherHeader" to "z"
        ))

        // First, we send the message that both flows want to see
        flowChannelA.send(messageY)
        flowChannelB.send(messageY)
        // Now "Message Y" is linked to two message groups, but since it's the same message, it only exists once in the MESSAGES table

        // Now "Message X" comes in, which is only needed in Flow A and which is the last message needed for the group of Flow A to release
        flowChannelA.send(messageX)
        // The group for Flow A is completed and hence deleted. The messages linked to this group are deleted as well, but the link
        // (group_to_message) for Group B for the deleted message stays in place

        // When we now send "Message Z" to Channel B, which should complete the group, the one message from before is already deleted from
        // the database, so the group doesn't complete correctly.
        // In the release strategy, we can even see that the size of the group is 2, while only one message can be retrieved from the database
        flowChannelB.send(messageZ)
    }

}

fun main(args: Array<String>) {
    runApplication<SpringIntegrationJdbcReproApplication>(*args)
}
