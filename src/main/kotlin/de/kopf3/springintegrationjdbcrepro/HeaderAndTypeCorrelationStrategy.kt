package de.kopf3.springintegrationjdbcrepro

import org.springframework.integration.aggregator.CorrelationStrategy
import org.springframework.messaging.Message

class HeaderAndTypeCorrelationStrategy(val type: String, val headerName: String) : CorrelationStrategy {
    override fun getCorrelationKey(message: Message<*>): Any {
        return HeaderAndTypeCorrelationKey(
            type,
            message.headers[headerName].toString(),
        )
    }
}
