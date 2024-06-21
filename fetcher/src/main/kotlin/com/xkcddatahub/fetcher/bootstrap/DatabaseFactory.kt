package com.xkcddatahub.fetcher.bootstrap

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.ApplicationConfig
import org.flywaydb.core.Flyway

class DatabaseFactory(config: ApplicationConfig) {
    private val url = config.property("ktor.database.url").getString()
    private val driver = config.property("ktor.database.driver").getString()
    private val username = config.property("ktor.database.user").getString()
    private val password = config.property("ktor.database.password").getString()

    fun init() {
        val connection = hikari()
//        Database.connect(connection)
        Flyway
            .configure()
            .dataSource(connection)
            .load()
            .migrate()
    }

    private fun hikari(): HikariDataSource {
        val config =
            HikariConfig().apply {
                jdbcUrl = url
                driverClassName = driver
                username = this@DatabaseFactory.username
                password = this@DatabaseFactory.password
                maximumPoolSize = 3
                isAutoCommit = true
                validate()
            }

        return HikariDataSource(config)
    }
}
