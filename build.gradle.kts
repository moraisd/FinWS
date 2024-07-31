plugins {
    java
    id("io.quarkus")
}

repositories {
    mavenLocal()
    mavenCentral()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

val lombokPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-mongodb-panache")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-smallrye-graphql")
    testImplementation("io.quarkus:quarkus-junit5-mockito")
    testImplementation("io.rest-assured:rest-assured")

    compileOnly("org.projectlombok:lombok:${lombokPlatformVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokPlatformVersion}")
    testCompileOnly("org.projectlombok:lombok:${lombokPlatformVersion}")
    testAnnotationProcessor("org.projectlombok:lombok:${lombokPlatformVersion}")
}

group = "org.moraisd"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
