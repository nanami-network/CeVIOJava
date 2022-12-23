package xyz.n7mn.dev.data;

import xyz.n7mn.dev.structure.TalkerComponentStructure;

import java.util.HashMap;
import java.util.Map;

public class TalkerComponentCollection {
    private final Map<String, TalkerComponentStructure> components = new HashMap<>();

    public TalkerComponentCollection(TalkerComponentStructure[] talkerComponentStructures) {
        for (TalkerComponentStructure component : talkerComponentStructures) {
            components.put(component.name, component);
        }
    }

    public TalkerComponentStructure getComponent(String id) {
        return components.get(id);
    }

    public Map<String, TalkerComponentStructure> getComponents() {
        return components;
    }
}
