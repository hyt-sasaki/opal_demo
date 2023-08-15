import org.jooq.meta.jaxb.ForcedType
import org.jooq.meta.jaxb.RegexFlag
import org.jooq.meta.jaxb.SchemaMappingType

plugins {
    id("spring.base.kotlin-library-conventions")
    id("jooq-codegen")
}

dependencies {
    implementation(libs.jooq)
}

jooqCodegen {
    packageName = "hytssk.ind.spring.base.jooq.generated"
    database {
        schemata = listOf(
            SchemaMappingType().apply {
                inputSchema = "sample"
                isOutputSchemaToDefault = true
            },
        )
        forcedTypes = listOf(
            ForcedType().apply {
                userType = "kotlin.Boolean"
                includeTypes = "(?i:TINYINT\\(1\\))"
            }
        )
        excludes = listOf("flyway_schema_history").joinToString(separator = "|")
        withRegexFlags(RegexFlag.COMMENTS)
    }
    jdbc {
        driver = "com.mysql.cj.jdbc.Driver"
        url = "jdbc:mysql://127.0.0.1:3306/sample?allowPublicKeyRetrieval=true&useSSL=false"
        user = "docker"
        password = "docker"
    }
}
