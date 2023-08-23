plugins {
   id("com.bmuschko.docker-java-application")
   `java-test-fixtures`
}

dependencies {
   api(projects.domain)

   // app
   api(rootProject.deps.bundles.hoplite)
   api(rootProject.deps.micrometer.core)
   api(rootProject.deps.micrometer.registry.datadog)

   // services
   api(rootProject.deps.bundles.ktor.server)
   api(rootProject.deps.ktor.serialization.jackson)

   // datastore
   api(rootProject.deps.postgresql)
   api(rootProject.deps.hikari)
   api(rootProject.deps.spring.jdbc)
   api(rootProject.deps.flyway.core)

   // health checks and info endpoints
   implementation(rootProject.deps.bundles.cohort)
}

dependencies {
   // datastore
   testFixturesImplementation(rootProject.deps.kotest.extensions.testcontainers)
   testFixturesImplementation(rootProject.deps.testcontainers.postgresql)

   // services
   testImplementation(rootProject.deps.ktor.server.test.host)
}

docker {
   javaApplication {
      baseImage.set(rootProject.deps.versions.dockerBaseImage)
      ports.set(listOf(8080))
      mainClassName.set("com.tarantini.pantry.app.MainKt")
      // standard JVM flags that use memory settings suitable for containers
      jvmArgs.set(
         listOf(
            "-Djava.security.egd=file:/dev/./urandom",
            "-XX:+UseContainerSupport",
            "-XX:MaxRAMPercentage=80",
         )
      )
   }
}
