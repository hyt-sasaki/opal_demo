plugins {
    id("spring.base.kotlin-application-conventions")
}

dependencies {
    implementation(libs.spring.boot.web)
    implementation(libs.spring.boot.webflux)
    implementation(project(":context:core"))
    runtimeOnly(project(":infra"))
}
