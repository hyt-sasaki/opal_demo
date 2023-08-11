import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

plugins {
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}
