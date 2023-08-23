package com.tarantini.pantry.services

import com.tarantini.pantry.datastore.flywayMigrate
import com.tarantini.pantry.datastore.postgresContainer
import io.kotest.extensions.testcontainers.SharedJdbcDatabaseContainerExtension

val postgres = SharedJdbcDatabaseContainerExtension(postgresContainer, afterStart = { flywayMigrate(it) })
