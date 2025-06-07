plugins {
    `java-gradle-plugin`
}

group = "com.github.vitamaxDH"
version = "0.2.1"

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
            displayName = "Gradle Dependency Visualizer"
            description = "A plugin to visualize module dependencies as an interactive HTML graph."
        }
    }
}
