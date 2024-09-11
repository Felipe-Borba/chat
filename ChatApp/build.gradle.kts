import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

group = "com.felipeborba"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing")
    implementation("com.rabbitmq:amqp-client:5.17.1")
    implementation("org.slf4j:slf4j-nop:2.0.9")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
            obfuscate.set(true)
        }
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ChatApp"
            packageVersion = "1.0.0"
            copyright = "© 2024 Felipe Borba. All rights reserved."
            vendor = "felipe-borba"
            includeAllModules = true
            outputBaseDir.set(project.buildDir.resolve("output"))
            appResourcesRootDir.set(project.layout.projectDirectory.dir("resources"))
        }
    }
}
