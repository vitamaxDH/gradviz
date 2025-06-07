plugins {
    `java-library`
}

dependencies {
    // Gradle API is needed for the task classes
    compileOnly(gradleApi())
    // Gson for JSON
    implementation("com.google.code.gson:gson:2.10.1")
} 