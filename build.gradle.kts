plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "1.2.1" // Use the latest available
}

group = "io.github.vitamaxdh"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
}

gradlePlugin {
    website.set("https://github.com/vitamaxdh/gradviz")
    vcsUrl.set("https://github.com/vitamaxdh/gradviz.git")
    plugins {
        create("gradviz") {
            id = "io.github.vitamaxdh.gradviz"
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
                description.set("JAR + POM for io.github.vitamaxdh:gradviz plugin")
                url.set("https://github.com/vitamaxdh/gradviz")
            }
        }
    }
}
