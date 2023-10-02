package com.tarantini.pantry.app

import com.sksamuel.cohort.Cohort
import com.tarantini.pantry.authentication.UserSession
import createRouting
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

/**
 * Creates the ktor server instance for this application.
 * We use the [Netty] engine implementation.
 *
 * @return the engine instance ready to be started.
 */
fun createNettyServer(config: Config, dependencies: Dependencies): NettyApplicationEngine {

   logger.info { "Creating Netty server @ https://localhost:${config.port}" }

   val server = embeddedServer(Netty, port = config.port) {

      // configures server side micrometer metrics
      // install(MicrometerMetrics) { registry = dependencies.registry }
      // allows foo/ and foo to be treated the same
      install(IgnoreTrailingSlash)
      // enables zip and deflate compression
      install(Compression)
      // setup json marshalling - provide your own jackson mapper if you have custom jackson modules
      install(ContentNegotiation) { jackson() }
      // enables strict security headers to force TLS
      // install(HSTS) { maxAgeInSeconds = 1.hours.inWholeSeconds }
      // enable CORS
      install(CORS) {
         anyHost()
         allowHeader(HttpHeaders.ContentType)
         allowHeader(HttpHeaders.Authorization)
         allowHeader(HttpHeaders.UserAgent)
         allowHeader(HttpHeaders.Referrer)
         allowHeader(HttpHeaders.Accept)
      }
      install(Sessions) {
         cookie<UserSession>("UserSession") {
            cookie.extensions["SameSite"] = "lax"
         }
      }
      // healthchecks and actuator endpoints
      install(Cohort) {
         gc = true
         jvmInfo = true
         sysprops = true
         threadDump = true
         heapDump = true
         healthcheck("/startup", startupProbes(dependencies.ds))
         healthcheck("/liveness", livenessProbes())
         healthcheck("/readiness", readinessProbes())
      }
      createAuthentication(config, dependencies)
      createRouting(dependencies)
   }

   server.addShutdownHook {
      server.stop(config.quietPeriod.inWholeMilliseconds, config.shutdownTimeout.inWholeMilliseconds)
   }

   return server
}

