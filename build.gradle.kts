plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.fasterxml.jackson.core:jackson-core:2.13.3")
    testImplementation("com.fasterxml.jackson.core:jackson-annotations:2.13.3")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    testCompileOnly ("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.24")
    testImplementation ("io.rest-assured:rest-assured:5.1.1")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}