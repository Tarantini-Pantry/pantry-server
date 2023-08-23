rootProject.name = "pantry-server"

include(
   ":app",
   ":client",
   ":domain",
)

pluginManagement {
   plugins {
      id("com.bmuschko.docker-java-application") version ("9.3.1")
   }
}

dependencyResolutionManagement {
   versionCatalogs {
      create("deps") {
         from(files("deps.versions.toml"))
      }
   }
}

enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
