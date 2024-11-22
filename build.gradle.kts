plugins {
    id("java")
    id("org.openapi.generator").version("7.0.1")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
//    implementation("org.openapitools:openapi-generator-gradle-plugin:7.0.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.register<Copy>("one") {
    println("one")
}