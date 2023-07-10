val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version : String by project
val h2_version : String by project

plugins {
    kotlin("jvm") version "1.8.22"
    id("io.ktor.plugin") version "2.3.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.22"
}

group = "com.mmuslimabdulj"
version = "0.0.1"
application {
    mainClass.set("com.mmuslimabdulj.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation ("io.ktor:ktor-serialization:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.mysql:mysql-connector-j:8.0.33")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.hibernate:hibernate-validator:8.0.1.Final")
    implementation("io.insert-koin:koin-ktor:3.4.1")
    implementation("org.ktorm:ktorm-core:3.6.0")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
}