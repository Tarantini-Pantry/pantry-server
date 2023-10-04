package com.tarantini.pantry.app

import com.sksamuel.cohort.Cohort
import com.tarantini.pantry.authentication.UserSession
import com.tarantini.pantry.domain.User
import com.tarantini.pantry.plugins.configureCohort
import com.tarantini.pantry.plugins.configureCors
import com.tarantini.pantry.plugins.configureGoogleJwt
import com.tarantini.pantry.plugins.configureSessions
import createRouting
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.hsts.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import mu.KotlinLogging
import kotlin.time.Duration.Companion.hours

private val logger = KotlinLogging.logger { }

/**
 * Creates the ktor server instance for this application.
 * We use the [Netty] engine implementation.
 *
 * @return the engine instance ready to be started.
 */
fun createNettyServer(config: Config, dependencies: Dependencies): NettyApplicationEngine {

   logger.info { "Creating Netty server @ https://localhost:${config.port}" }

   val server = embeddedServer(Netty, port = config.port, module = function(dependencies, config))

   server.addShutdownHook {
      server.stop(config.quietPeriod.inWholeMilliseconds, config.shutdownTimeout.inWholeMilliseconds)
   }

   return server
}

private fun function(
   dependencies: Dependencies,
   config: Config
): Application.() -> Unit = {

   // configures server side micrometer metrics
   // install(MicrometerMetrics) { registry = dependencies.registry }
   // allows foo/ and foo to be treated the same
   install(IgnoreTrailingSlash)
   // enables zip and deflate compression
   install(Compression)
   // setup json marshalling - provide your own jackson mapper if you have custom jackson modules
   install(ContentNegotiation) { jackson() }
   // enables strict security headers to force TLS
    install(HSTS) {
       maxAgeInSeconds = 1.hours.inWholeSeconds
    }
   // enable CORS
   install(CORS) {
      configureCors()
   }
   install(Sessions) {
      configureSessions(config)
   }
   // health checks and actuator endpoints
   install(Cohort) {
      configureCohort(dependencies)
   }
   install(Authentication) {
      configureGoogleJwt(config)
   }
   createRouting(dependencies)
}

