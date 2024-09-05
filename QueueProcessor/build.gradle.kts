plugins {
    id("java")
}

group = "com.felipeborba"
version = "1.0-SNAPSHOT"

//java {
//    sourceCompatibility = JavaVersion.VERSION_19
//    targetCompatibility = JavaVersion.VERSION_19
//}

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

tasks.test {
    useJUnitPlatform()
}
