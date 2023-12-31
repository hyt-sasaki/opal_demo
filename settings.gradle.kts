pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

rootProject.name = "hytssk.ind.spring.base"
include("app", "infra", "context:core", "auth_data", "flyway", "jooq")
