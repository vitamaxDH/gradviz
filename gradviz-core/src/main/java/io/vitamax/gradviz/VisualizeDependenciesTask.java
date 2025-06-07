package io.vitamax.gradviz;

import com.google.gson.Gson;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class VisualizeDependenciesTask extends DefaultTask {

    @OutputFile
    public abstract RegularFileProperty getOutputFile();

    @Input
    @Optional
    public abstract Property<String> getModulePath();

    @Input
    public abstract ListProperty<String> getConfigurations();

    @Input
    public abstract Property<Boolean> getOpenReport();

    public VisualizeDependenciesTask() {
        getOutputFile().set(getProject().getLayout().getBuildDirectory().file("reports/dependency-graph/index.html"));
        getConfigurations().convention(Arrays.asList("runtimeClasspath"));
        getOpenReport().convention(true);
    }

    @TaskAction
    public void run() throws IOException {
        List<ModuleReport> allModuleReports = new ArrayList<>();

        List<Project> targetProjects;
        if (getModulePath().isPresent()) {
            String path = getModulePath().get();
            // Special keyword 'all' to analyze all projects
            if ("all".equalsIgnoreCase(path)) {
                targetProjects = new ArrayList<>();
                targetProjects.add(getProject().getRootProject());
                targetProjects.addAll(getProject().getRootProject().getSubprojects());
            } else {
                Project target = getProject().getRootProject().findProject(path);
                if (target == null) {
                    throw new GradleException("Module with path '" + path + "' not found. Make sure it's a valid project path (e.g., ':app').");
                }
                targetProjects = Arrays.asList(target);
            }
        } else {
            // Default behavior: analyze the current project where the task is executed
            targetProjects = Arrays.asList(getProject());
        }

        for (Project p : targetProjects) {
            Configuration configuration = getConfigurations().get().stream()
                .map(name -> p.getConfigurations().findByName(name))
                .filter(c -> c != null && c.isCanBeResolved())
                .findFirst()
                .orElse(null);

            if (configuration != null) {
                getLogger().lifecycle("🚀 Processing module '" + p.getPath() + "' with configuration '" + configuration.getName() + "'");
                DependencyNode rootNode = buildDependencyTree(p, configuration);
                allModuleReports.add(new ModuleReport(p.getName(), configuration.getName(), rootNode));
            } else {
                getLogger().warn("⚠️ Could not find a resolvable configuration for module '" + p.getPath() + "'. Skipping.");
            }
        }

        if (allModuleReports.isEmpty()) {
            getLogger().warn("No modules were processed. Report will not be generated.");
            return;
        }

        String jsonData = new Gson().toJson(allModuleReports);
        generateReport(jsonData);

        URI reportUri = getOutputFile().get().getAsFile().toURI();
        getLogger().lifecycle("✅ Dependency report generated at: " + reportUri);
        
        if (getOpenReport().get()) {
            try {
                Desktop.getDesktop().browse(reportUri);
            } catch (Exception e) {
                getLogger().warn("Could not open report in browser: " + e.getMessage());
            }
        }
    }

    private DependencyNode buildDependencyTree(Project project, Configuration configuration) {
        Set<String> visited = new HashSet<>();
        List<DependencyNode> children = configuration.getResolvedConfiguration().getFirstLevelModuleDependencies()
            .stream()
            .map(dep -> convert(dep, visited))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        return new DependencyNode(project.getPath() + "-" + configuration.getName(), project.getName(), children);
    }

    private DependencyNode convert(ResolvedDependency dependency, Set<String> visited) {
        String id = dependency.getModule().getId().toString();
        if (visited.contains(id)) {
            return null; // Cycle detected in this path
        }
        
        visited.add(id); // Mark as visited for this path

        List<DependencyNode> children = dependency.getChildren().stream()
            .map(child -> convert(child, visited))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
            
        visited.remove(id); // Un-mark so it can be visited in other paths

        return new DependencyNode(id, id, children);
    }

    private void generateReport(String jsonData) throws IOException {
        InputStream templateStream = getClass().getResourceAsStream("/gradviz_template.html");
        if (templateStream == null) {
            getLogger().error("Failed to find 'gradviz_template.html' in resources.");
            return;
        }

        String template;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(templateStream))) {
            template = reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }

        String finalHtml = template.replace("%%DATA%%", jsonData);
        
        File reportFile = getOutputFile().get().getAsFile();
        reportFile.getParentFile().mkdirs();
        
        try (FileWriter writer = new FileWriter(reportFile)) {
            writer.write(finalHtml);
        }
    }
} 