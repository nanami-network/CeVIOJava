package xyz.n7mn.dev.data;

import xyz.n7mn.dev.structure.TalkerComponentStructure;

import java.util.HashMap;
import java.util.Map;

public class TalkerComponentCollection {
    private final Map<String, TalkerComponent> components = new HashMap<>();

    public TalkerComponentCollection(CastSettings settings, TalkerComponentStructure[] talkerComponentStructures) {
        for (TalkerComponentStructure component : talkerComponentStructures) {
            components.put(component.name, new TalkerComponent(settings, component));
        }
    }

    public TalkerComponent getComponent(String id) {
        return components.get(id);
    }

    public Map<String, TalkerComponent> getComponents() {
        return components;
    }
}
