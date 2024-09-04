plugins {
    id("java")
}

group = "com.felipeborba"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.rabbitmq:amqp-client:5.15.0")
}

tasks.test {
    useJUnitPlatform()
}