plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.felipeborba"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.rabbitmq:amqp-client:5.17.1")
    implementation("org.slf4j:slf4j-nop:2.0.9")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

tasks {
    shadowJar {
        archiveBaseName.set("QueueProcessor")
        archiveVersion.set("1.0-SNAPSHOT")

        manifest {
            attributes["Main-Class"] = "com.felipeborba.Main"
        }
    }
}
tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.test {
    useJUnitPlatform()
}
