import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import java.nio.charset.StandardCharsets

// Kill me
fun parseDotEnv(file: File): Map<String,String> {
    if (!file.exists()) return emptyMap()
    return file.readLines(StandardCharsets.UTF_8).mapNotNull { line ->
        val t = line.trim()
        if (t.isEmpty() || t.startsWith("#")) return@mapNotNull null
        val noExport = if (t.startsWith("export ")) t.removePrefix("export ").trim() else t
        val (k, v) = noExport.split("=", limit = 2).let { it[0].trim() to (it.getOrNull(1)?.trim() ?: "") }
        val unquoted = v.removeSurrounding("\"").removeSurrounding("'")
        k to unquoted
    }.toMap()
}

val nvdApiKeyFromEnv: String? = providers.environmentVariable("NVD_API_KEY").orNull
val nvdApiKeyFromSys: String? = providers.systemProperty("nvdApiKey").orNull
val nvdApiKeyFromGradleProp: String? = providers.gradleProperty("nvdApiKey").orNull

val projectDotEnv = rootProject.file(".env")
val envMap = parseDotEnv(projectDotEnv)

val nvdApiKey: String? = nvdApiKeyFromEnv
    ?: nvdApiKeyFromSys
    ?: nvdApiKeyFromGradleProp
    ?: envMap["NVD_API_KEY"]
    ?: envMap["nvdApiKey"]

spotbugs {
    toolVersion = "6.4.2"
    ignoreFailures = false
    showStackTraces = true
    showProgress = true
    effort = Effort.MAX
    reportLevel = Confidence.MEDIUM
}

dependencyCheck {
    format = "HTML"
    nvd {
        apiKey = nvdApiKey
        delay = 1000
    }
    analyzers {
        centralEnabled = true
        ossIndex {
            enabled = false
        }
        nodeAudit {
            enabled = false
        }
        retirejs {
            enabled = false
        }
    }

    failBuildOnCVSS = 7.0F
    outputDirectory.set(layout.buildDirectory.dir("reports/dependency-check"))
    scanConfigurations = listOf("runtimeClasspath", "compileClasspath")
}

plugins {
    java
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.spotbugs") version "6.4.2"
    id("org.owasp.dependencycheck") version "12.1.8"
}

group = "ru.itmo.cs.dandadan"
version = "1.0"
description = "MagicItems"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

configure<org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension> {
    format = org.owasp.dependencycheck.reporting.ReportGenerator.Format.ALL.toString()
}

repositories {
    mavenCentral()
}

buildscript {
    extra.apply {
        set("spotbugsVersion", "4.9.6")
        set("jwtVersion", "0.13.0")
    }
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

dependencies {
    val spotbugsVersion = rootProject.extra["spotbugsVersion"]
    val jwtVersion = rootProject.extra["jwtVersion"]

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")

    implementation("io.jsonwebtoken:jjwt-api:${jwtVersion}")
    implementation("io.jsonwebtoken:jjwt-impl:${jwtVersion}")
    implementation("io.jsonwebtoken:jjwt-jackson:${jwtVersion}")
    implementation("me.paulschwarz:spring-dotenv:4.0.0")
    spotbugs("com.github.spotbugs:spotbugs:${spotbugsVersion}")
    spotbugsPlugins("com.h3xstream.findsecbugs:findsecbugs-plugin:1.14.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.spotbugsMain {
    reports.create("xml") {
        required.set(true)
        outputLocation.set(file("reports/spotbugs.xml"))
    }
    reports.create("html") {
        required.set(true)
        outputLocation.set(file("reports/spotbugs.html"))
    }
}
