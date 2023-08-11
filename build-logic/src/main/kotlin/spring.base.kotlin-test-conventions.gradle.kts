import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
    kotlin("jvm")
}

testing {
    suites {
        @Suppress("UnstableApiUsage") val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
            dependencies {
                implementation(libs.spring.boot.test)
                implementation(libs.kotlin.test.junit5)
            }
        }
    }
}
