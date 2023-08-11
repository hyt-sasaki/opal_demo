plugins {
    id("spring.base.kotlin-library-conventions")
}

dependencies {
    implementation(project(":context:core"))
    implementation(libs.spring.boot.autoconfigure)
}