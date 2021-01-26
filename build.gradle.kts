import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    application
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

group = "com.edgelab.hospital"
version = "2.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.mockk:mockk:1.12.4")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

// https://stackoverflow.com/questions/41794914/how-to-create-a-fat-jar-with-gradle-kotlin-script#:~:text=Method%202%3A%20Embedding%20the%20libraries%20in%20the%20result%20JAR%20file%20(fat%20or%20uber%20JAR)
tasks.jar {
    manifest.attributes["Main-Class"] = "com.edgelab.hospital.ApplicationKt"
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
