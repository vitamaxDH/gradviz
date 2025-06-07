plugins {
    `java-gradle-plugin`
    base
}

group = "com.github.vitamaxDH"
version = "0.4.1"

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
            displayName = "Gradviz"
            description = "Visualize Gradle module dependencies as an HTML graph"
        }
    }
}
