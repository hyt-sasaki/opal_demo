import org.yaml.snakeyaml.Yaml

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(libs.flyway.mysql)
        classpath(libs.mysql)
        classpath(libs.yaml)
    }
}

plugins {
    alias(libs.plugins.flyway)
}

flyway {
    val setting: Map<String, String> = Yaml().load(file("./db.yaml").inputStream())
    url = System.getenv("MYSQL_URL") ?: setting.getValue("url")
    user = System.getenv("MYSQL_USER") ?: setting.getValue("user")
    password = System.getenv("MYSQL_PASSWORD") ?: setting.getValue("password")
    locations = arrayOf("filesystem:./migrations/v1")
}