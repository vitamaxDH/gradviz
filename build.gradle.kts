plugins {
    `java-gradle-plugin`
}

group = "io.vitamax.gradviz"
version = "0.1.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
}

gradlePlugin {
    plugins {
        create("gradviz") {
            id = "io.vitamax.gradviz"
            implementationClass = "io.vitamax.gradviz.GradvizPlugin"
            displayName = "Gradle Dependency Visualizer"
            description = "A plugin to visualize module dependencies as an interactive HTML graph."
        }
    }
}
