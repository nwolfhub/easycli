package org.nwolfhub;

import java.util.HashMap;
import java.util.List;

public class EasyCLI {
    private HashMap<String, Template> templates = new HashMap<>();
    private HashMap<String, InputTask> tasks = new HashMap<>();
    public EasyCLI() {

    }

    public void addTemplate(Template template) {
        templates.put(template.getName(), template);
    }
    public void removeTemplate(String name) {
        templates.remove(name);
    }

    public List<Object> getTemplates() {
        return List.of(templates.values().toArray());
    }
}
