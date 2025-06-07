# 🕸️ GradViz - Gradle Dependency Visualizer

GradViz is a Gradle plugin that generates an interactive HTML report to visualize project dependencies. It helps you understand the complex dependency graph of your multi-module or single-module project.

---

## 🚀 Installation

Add the plugin to your `build.gradle.kts` (or `build.gradle`).

**Kotlin DSL (`build.gradle.kts`)**
```kotlin
plugins {
    id("com.github.vitamaxDH.gradviz") version "0.1.0"
}
```

**Groovy DSL (`build.gradle`)**
```groovy
plugins {
    id 'com.github.vitamaxDH.gradviz' version '0.1.0'
}
```
*After the plugin is successfully published to the Gradle Plugin Portal, users will not need to add any custom repositories.*

---

## ✨ Features

-   **Interactive Graph**: Uses G6.js to create a zoomable, pannable, and searchable graph.
-   **Multi-Module Support**: Analyze a single module, a specific module, or all modules in your project.
-   **Configurable**: Customize the Gradle configuration to analyze, and control how the report is generated.
-   **Easy to Use**: Get started quickly with sensible defaults.

---

## 🚀 How to Use

To use this plugin in your project (e.g., a project named `my-app`), you first need to include the plugin's build and then apply it.

### 1. Include the Plugin Build

In your project's `settings.gradle.kts` file, add an `includeBuild` pointing to the directory where you cloned the `gradviz` plugin.

```kotlin
// settings.gradle.kts of my-app

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
rootProject.name = "my-app"

// Add this line to include the local gradviz plugin
includeBuild("/path/to/your/gradviz-project-folder")
```

### 2. Apply the Plugin

In your project's `build.gradle.kts` file, apply the plugin using its ID.

```kotlin
// build.gradle.kts of my-app

plugins {
    // ... your other plugins
    id("io.vitamax.gradviz")
}
```

---

## ▶️ Running the Task

Once the plugin is applied, the `gradViz` task becomes available.

To generate a report for the current module, simply run:

```bash
./gradlew gradViz
```

By default, this will:
- Analyze the dependencies of the module where the task is run.
- Use the `runtimeClasspath` configuration.
- Generate an `index.html` report in `build/reports/dependency-graph/`.
- Open the report in your default browser automatically.

---

## ⚙️ Configuration

You can configure the task either via the command line (for quick changes) or in your `build.gradle.kts` (for permanent settings).

### Command-Line Properties

Use `-P` to pass project properties to Gradle.

-   **`modulePath`**: Specifies which module(s) to analyze.
    -   `-PmodulePath=all`: Analyzes the root project and all its subprojects.
    -   `-PmodulePath=:app`: Analyzes the subproject named `app`.
    -   *(Default)*: If not provided, analyzes only the current project.

-   **`openReport`**: Controls whether the report is opened automatically.
    -   `-PopenReport=false`: Disables opening the report in the browser.
    -   *(Default)*: `true`.

### Task Configuration (build.gradle.kts)

For more permanent configurations, you can configure the `gradViz` task directly in your `build.gradle.kts`.

```kotlin
// build.gradle.kts

tasks.named<io.vitamax.gradviz.VisualizeDependenciesTask>("gradViz") {
    // A list of Gradle configurations to look for, in order of preference.
    // The plugin will use the first one it finds.
    configurations.set(listOf("releaseCompileClasspath", "implementation"))

    // Set the module to analyze. Overrides the default (current module).
    modulePath.set(":app") // or "all"

    // Disable opening the report automatically.
    openReport.set(false)
}
```

---

## 💡 Examples

### Example 1: Analyze the current module (default)

Run this from your project's root or a specific submodule directory.

```bash
./gradlew :app:gradViz
# Or from the :app directory
# ./gradlew gradViz
```
This generates a report for the `:app` module using its `runtimeClasspath`.

### Example 2: Analyze all modules in the project

Run this from the project's root directory.

```bash
./gradlew gradViz -PmodulePath=all
```
This generates a report containing tabs for every submodule.

### Example 3: Analyze a specific module and don't open the report

```bash
./gradlew gradViz -PmodulePath=:core -PopenReport=false
```
This generates a report for the `:core` module and prints the file path to the console without opening it.

### Example 4: Configure a custom task for Android release dependencies

You can register a separate task for a specific purpose.

```kotlin
// build.gradle.kts

tasks.register<io.vitamax.gradviz.VisualizeDependenciesTask>("releaseGraph") {
    group = "Reporting"
    description = "Generates a dependency graph for the release build."
    configurations.set(listOf("releaseRuntimeClasspath"))
    modulePath.set(":app")
}
```

Now you can run `./gradlew releaseGraph` to get a graph specifically for your release configuration.

---

## 🏗️ Project Structure

The project is organized into a multi-module structure:

-   `gradviz-core`: Contains the main plugin logic, including dependency analysis and report generation.
-   `gradviz-marker`: A marker artifact used for publishing to the Gradle Plugin Portal. It ensures that consumers can apply the plugin using the `plugins { ... }` block.

---

## 🛠️ Development Setup (for contributors)

If you want to contribute to this plugin, you can use `includeBuild` to test your local changes.

In your test project's `settings.gradle.kts` file, add an `includeBuild` pointing to your local `gradviz` directory.

```kotlin
// settings.gradle.kts of my-app

// Add this line to include the local gradviz plugin
includeBuild("/path/to/your/gradviz-project-folder")
```

Then, in your test project's `build.gradle.kts`, apply the plugin using the ID defined in the `gradviz` build file.

```kotlin
// build.gradle.kts of my-app

plugins {
    // ... your other plugins
    id("io.vitamax.gradviz")
}
``` 