package de.kopf3.springintegrationjdbcrepro

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.MessageChannels

@Configuration
class Channels {

    @Bean
    fun flowChannelA() = MessageChannels.publishSubscribe("channelA").getObject()

    @Bean
    fun flowChannelB() = MessageChannels.publishSubscribe("channelB").getObject()

}
