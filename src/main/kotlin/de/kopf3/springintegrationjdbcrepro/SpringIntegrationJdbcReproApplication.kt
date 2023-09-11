package de.kopf3.springintegrationjdbcrepro

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.jdbc.store.JdbcMessageStore
import javax.sql.CommonDataSource
import javax.sql.DataSource

@SpringBootApplication
@EnableIntegration
class SpringIntegrationJdbcReproApplication {

    @Bean
    @Scope("prototype")
    fun jdbcMessageStore(dataSource: DataSource) = JdbcMessageStore(dataSource)

    @Bean
    fun runner(): CommandLineRunner = CommandLineRunner {
        println("Hello")
    }

}

fun main(args: Array<String>) {
    runApplication<SpringIntegrationJdbcReproApplication>(*args)
}
