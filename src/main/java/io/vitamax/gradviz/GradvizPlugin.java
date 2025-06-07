package io.vitamax.gradviz;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GradvizPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getTasks().register("gradViz", VisualizeDependenciesTask.class, task -> {
            task.setGroup("Reporting");
            task.setDescription("Generates an interactive dependency graph. Can be focused on a single module with -PmodulePath=:path");
        });
    }
} 
