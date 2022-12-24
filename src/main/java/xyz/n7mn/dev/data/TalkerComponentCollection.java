package xyz.n7mn.dev.data;

import xyz.n7mn.dev.structure.TalkerComponentStructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public Map<String, TalkerComponent> getAsMap() {
        return components;
    }

    public Collection<TalkerComponent> getAsCollection() {
        return components.values();
    }
}
