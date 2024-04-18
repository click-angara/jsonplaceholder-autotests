plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    compileOnly("io.rest-assured:rest-assured:5.4.0")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
    testImplementation("junit:junit:4.13.2")
}

tasks.test {
    useJUnitPlatform()
}