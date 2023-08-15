import gradle.kotlin.dsl.accessors._4fd4f744a2474c50f4bf77c988d14018.kotlin
import gradle.kotlin.dsl.accessors._4fd4f744a2474c50f4bf77c988d14018.sourceSets
import org.gradle.accessors.dm.LibrariesForLibs
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Target

abstract class JooqCodegenExtension {
    abstract val packageName: Property<String>
    abstract val database: Property<Database>
    fun database(configure: Database.() -> Unit) {
        database.set(Database().apply { configure() })
    }

    abstract val jdbc: Property<Jdbc>
    fun jdbc(configure: Jdbc.() -> Unit) {
        jdbc.set(Jdbc().apply { configure() })
    }
}

val extension = project.extensions.create("jooqCodegen", JooqCodegenExtension::class)
val targetDir = "src/generated/kotlin"
project.tasks.register("jooqCodegen") {
    val destDir = "${project.projectDir}/$targetDir"
    delete(destDir)
    val config = Configuration().apply {
        jdbc = extension.jdbc.get()
        logging = Logging.ERROR
        generator = Generator().apply {
            name = "org.jooq.codegen.KotlinGenerator"
            database = extension.database.get()
            generate = Generate().apply {
                isKotlinNotNullRecordAttributes = true
            }
            target = Target().apply {
                packageName = extension.packageName.get()
                directory = destDir
            }
        }
    }
    GenerationTool.generate(config)
}

plugins {
    kotlin("jvm")
}

sourceSets {
    kotlin {
        sourceSets["main"].kotlin.srcDir(targetDir)
    }
}
