plugins {
    kotlin("jvm") version "2.1.10"
}

group = "lab2"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}



dependencies {
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    implementation("org.postgresql:postgresql:42.7.1")
    testImplementation("org.postgresql:postgresql:42.7.1")
    testImplementation("org.testcontainers:testcontainers:1.19.6")
    testImplementation("org.testcontainers:junit-jupiter:1.19.6")
    testImplementation("org.testcontainers:postgresql:1.19.6")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation(kotlin("test"))
}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}