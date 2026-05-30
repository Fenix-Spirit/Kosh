plugins {
    kotlin("jvm") version "2.4.0-RC"
    application
}

group = "com.github.kosh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(26)
}

sourceSets {
    main {
        kotlin {
            setSrcDirs(listOf("src"))
        }
    }
}

application {
    mainClass.set("MainKt")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {
        // Enable parallel backend for faster compilation
        freeCompilerArgs.add("-Xbackend-threads=0")
    }
}

tasks.test {
    useJUnitPlatform()
}
