plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "1.2.1" // Use the latest available
}

group = "io.github.vitamaxDH"
version = "0.2.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
}

gradlePlugin {
    website.set("https://github.com/vitamaxDH/gradviz")
    vcsUrl.set("https://github.com/vitamaxDH/gradviz.git")
    plugins {
        create("gradviz") {
            id = "io.github.vitamaxDH.gradviz"
            displayName = "Gradviz Plugin"
            description = "A Gradle plugin for visualizing Gradle builds"
            implementationClass = "io.vitamax.gradviz.GradvizPlugin"
            tags.set(listOf("visualization", "gradle dependency", "build"))
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = project.name
            pom {
                name.set("Gradviz Plugin Marker & Artifact")
                description.set("JAR + POM for io.github.vitamaxDH:gradviz plugin")
                url.set("https://github.com/vitamaxDH/gradviz")
            }
        }
    }
}
