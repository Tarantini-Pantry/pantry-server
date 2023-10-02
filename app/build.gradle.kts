import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
   `java-test-fixtures`
   alias(deps.plugins.shadow)
   id("java")
}

dependencies {
   api(projects.domain)

   // app
   api(rootProject.deps.jbcrypt)
   api(rootProject.deps.bundles.hoplite)
   api(rootProject.deps.micrometer.core)
   api(rootProject.deps.micrometer.registry.datadog)

   // services
   api(rootProject.deps.bundles.ktor.server)
   api(rootProject.deps.ktor.server.cors)
   api(rootProject.deps.ktor.serialization.jackson)

   // datastore
   api(rootProject.deps.postgresql)
   api(rootProject.deps.hikari)
   api(rootProject.deps.spring.jdbc)
   api(rootProject.deps.flyway.core)

   // health checks and info endpoints
   implementation(rootProject.deps.bundles.cohort)
}

tasks {
   withType<Jar> {
      manifest {
         attributes["Main-Class"] = "com.tarantini.pantry.app.MainKt"
      }
   }

   withType<ShadowJar> {
      archiveBaseName.set(providers.gradleProperty("app_name"))
      archiveClassifier.set("")
      archiveVersion.set(providers.gradleProperty("version"))
      mergeServiceFiles()
   }
}


dependencies {
   implementation("io.ktor:ktor-server-auth-jvm:2.3.4")
   implementation("io.ktor:ktor-server-core-jvm:2.3.4")
   implementation("io.ktor:ktor-client-core-jvm:2.3.4")
   implementation("io.ktor:ktor-client-apache-jvm:2.3.4")
   implementation("io.ktor:ktor-client-okhttp-jvm:2.3.4")
   implementation("io.ktor:ktor-server-sessions-jvm:2.3.4")// datastore
   testFixturesImplementation(rootProject.deps.kotest.extensions.testcontainers)
   testFixturesImplementation(rootProject.deps.testcontainers.postgresql)

   // services
   testImplementation(rootProject.deps.ktor.server.test.host)
}
