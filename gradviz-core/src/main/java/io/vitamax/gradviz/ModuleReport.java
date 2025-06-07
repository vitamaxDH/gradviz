package io.vitamax.gradviz;

public class ModuleReport {
    private final String moduleName;
    private final String configuration;
    private final DependencyNode tree;

    public ModuleReport(String moduleName, String configuration, DependencyNode tree) {
        this.moduleName = moduleName;
        this.configuration = configuration;
        this.tree = tree;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getConfiguration() {
        return configuration;
    }

    public DependencyNode getTree() {
        return tree;
    }
} 