plugins {
    id("spring.base.kotlin-application-conventions")
}

dependencies {
    implementation(libs.spring.boot.web)
    implementation(project(":context:core"))
    runtimeOnly(project(":infra"))
}
