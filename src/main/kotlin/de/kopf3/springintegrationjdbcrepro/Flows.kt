package de.kopf3.springintegrationjdbcrepro

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.aggregator.HeaderAttributeCorrelationStrategy
import org.springframework.integration.dsl.integrationFlow
import org.springframework.integration.jdbc.store.JdbcMessageStore
import org.springframework.messaging.MessageChannel

@Configuration
class Flows {

    @Bean
    fun baseFlowTypeA(
        flowChannelA: MessageChannel,
        jdbcMessageStore: JdbcMessageStore,
    ) = integrationFlow(flowChannelA) {
        aggregate {
            messageStore(jdbcMessageStore)
            correlationStrategy(HeaderAttributeCorrelationStrategy("someHeader"))
            releaseStrategy(MessageTypesReleaseStrategy("Flow A", listOf("x", "y")))
            expireGroupsUponCompletion(true)
        }
        handle {
            println("Aggregation done")
            println(it)
        }
    }

    @Bean
    fun baseFlowTypeB(
        flowChannelB: MessageChannel,
        jdbcMessageStore: JdbcMessageStore,
    ) = integrationFlow(flowChannelB) {
        aggregate {
            messageStore(jdbcMessageStore)
            correlationStrategy(HeaderAttributeCorrelationStrategy("someHeader"))
            releaseStrategy(MessageTypesReleaseStrategy("Flow B", listOf("y", "z")))
            expireGroupsUponCompletion(true)
        }
        handle {
            println("Aggregation done")
            println(it)
        }
    }

}
