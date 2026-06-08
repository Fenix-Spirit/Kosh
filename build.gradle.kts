import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
plugins {
    kotlin("multiplatform") version "2.1.20"
}
group = "com.spiritfenix.kosh"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
}
val isMac = System.getProperty("os.name").contains("Mac", ignoreCase = true)
kotlin {
    linuxX64 {
        binaries { executable { entryPoint = "main" } }
    }
    mingwX64 {
        binaries { executable { entryPoint = "main" } }
    }
    if (isMac) {
        macosArm64 {
            binaries { executable { entryPoint = "main" } }
        }
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
                implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.6.0")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.register("buildAll") {
    dependsOn("linuxX64Binaries", "mingwX64Binaries")
    if (isMac) dependsOn("macosArm64Binaries")
}
tasks.wrapper {
    gradleVersion = "9.5.0"
    distributionType = Wrapper.DistributionType.BIN
}