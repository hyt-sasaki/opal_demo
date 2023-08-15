plugins {
    id("spring.base.kotlin-library-conventions")
}

dependencies {
    implementation(project(":context:core"))
    implementation(project(":jooq"))
    implementation(libs.spring.boot.jooq)
    runtimeOnly(libs.mysql)
    implementation(libs.spring.boot.autoconfigure)
}
