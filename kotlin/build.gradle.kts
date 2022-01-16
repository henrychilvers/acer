import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "com.chilvers.henry"
version = "1.0.1-SNAPSHOT"

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "com.chilvers.henry.acer.AcerApplicationKt"
    }
    archiveFileName.set("acer.jar")

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("de.vandermeer:asciitable:0.3.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("AcerApplicationKt")
}
