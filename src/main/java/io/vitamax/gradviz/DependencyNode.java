package io.vitamax.gradviz;

import java.util.Collections;
import java.util.List;

public class DependencyNode {
    private final String id;
    private final String label;
    private final List<DependencyNode> children;

    public DependencyNode(String id, String label, List<DependencyNode> children) {
        this.id = id;
        this.label = label;
        this.children = children != null ? children : Collections.emptyList();
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public List<DependencyNode> getChildren() {
        return children;
    }
} 