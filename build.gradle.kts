plugins {
    kotlin("jvm") version "2.4.0-RC"
    application
}
group = "com.spiritfenix.kosh"
version = "0.2.1"
repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
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
        freeCompilerArgs.add("-Xbackend-threads=0")
    }
}

tasks.test {
    useJUnitPlatform()
}
