plugins {
    `java`
    `maven-publish`
}

group = "com.github.vitamaxDH"
version = "0.4.1"

publishing {
    publications {
        create<MavenPublication>("pluginMarker") {
            artifactId = "com.github.vitamaxDH.gradviz.gradle.plugin"
            pom {
                name.set("Gradviz Plugin Marker")
                description.set("Marker artifact for com.github.vitamaxDH.gradviz plugin")
            }
        }
    }
}
