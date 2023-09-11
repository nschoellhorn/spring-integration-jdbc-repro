package de.kopf3.springintegrationjdbcrepro

import org.springframework.integration.aggregator.ReleaseStrategy
import org.springframework.integration.store.MessageGroup

class MessageTypesReleaseStrategy(val sourceFlow: String, val requiredTypes: List<String>) : ReleaseStrategy {

    override fun canRelease(group: MessageGroup): Boolean {
        println("[$sourceFlow] Messages Size: ${group.messages.size}")
        println("[$sourceFlow] Messages: ${group.messages.toList()}")

        return group.messages.flatMap {
            msg ->
            msg.headers.filterKeys { it == "otherHeader" }
                .map { it.value }
        }.containsAll(this.requiredTypes)
    }

}
