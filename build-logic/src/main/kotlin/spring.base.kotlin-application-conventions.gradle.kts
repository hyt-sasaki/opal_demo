import de.undercouch.gradle.tasks.download.Download
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("spring.base.kotlin-common-conventions")
    id("org.springframework.boot")
    id("de.undercouch.download")
}

val libs = the<LibrariesForLibs>()
val agentVersion: String = libs.versions.otel.agent.getOrElse("1.21.0")
val agentPath = "${project.buildDir}/otel/opentelemetry-javaagent-$agentVersion.jar"

@Suppress("MaxLineLength")
tasks.register<Download>("downloadAgent") {
    src(
        "https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v$agentVersion/opentelemetry-javaagent.jar"
    )
    dest(agentPath)
}

tasks.getByName<JavaExec>("bootRun") {
    dependsOn("downloadAgent")
    jvmArgs = listOf(
        "-javaagent:$agentPath",
        "-Dotel.service.name=${project.displayName}-service"
    )
}
