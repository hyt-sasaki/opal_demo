plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(19)
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin)
    implementation(libs.kotlin.spring)
    implementation(libs.detekt.plugin)
    implementation(libs.spring.boot.plugin)
    implementation(libs.shadow.plugin)
    implementation(libs.download.plugin)
    runtimeOnly(libs.mysql)
    implementation(libs.jooq.codegen)
    implementation(libs.jooq)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}
