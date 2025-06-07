plugins {
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.2.1"
}

dependencies {
    implementation(project(":gradviz-core"))
}

gradlePlugin {
    website.set("https://github.com/vitamaxDH/gradviz")
    vcsUrl.set("https://github.com/vitamaxDH/gradviz")
    plugins {
        create("gradviz") {
            id = "com.github.vitamaxDH.gradviz"
            implementationClass = "io.vitamax.gradviz.GradvizPlugin"
            displayName = "Gradle Dependency Visualizer"
            description = "A plugin to visualize module dependencies as an interactive HTML graph."
        }
    }
}

pluginBundle {
    tags = listOf("visualization", "dependency", "graph", "dependencies")
} 