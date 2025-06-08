plugins {
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.github.vitamaxDH"
version = "0.1.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
}

gradlePlugin {
    plugins {
        create("gradviz") {
            id = "com.github.vitamaxDH.gradviz"
            implementationClass = "io.vitamax.gradviz.GradvizPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            // (원한다면) artifactId, pom metadata 등은 그대로 유지
            artifactId = project.name
            pom {
                name.set("Gradviz Plugin Marker & Artifact")
                description.set("JAR + POM for com.github.vitamaxDH:gradviz plugin")
            }
        }
    }
}
