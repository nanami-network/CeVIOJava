package xyz.n7mn.dev.data;

import xyz.n7mn.dev.CeVIOJava;
import xyz.n7mn.dev.structure.TalkerComponentStructure;

public class TalkerComponent {
    private final CeVIOJava cevio;
    private final CastSettings settings;
    private TalkerComponentStructure structure;

    public TalkerComponent(CastSettings settings, TalkerComponentStructure structure) {
        this.cevio = settings.getCevio();
        this.settings = settings;
        this.structure = structure;
    }

    public String getName() {
        return structure.name;
    }

    public String getId() {
        return structure.id;
    }

    public int getValue() {
        sync();
        return this.structure.value;
    }

    public TalkerComponent setValue(int value) {
        this.structure.value = value;
        write();
        return this;
    }

    public void sync() {
        this.structure = cevio.getTalkerComponentStructure(settings, structure.name);
    }

    public void write() {
        cevio.setTalkerComponent(settings, this);
    }

    public CeVIOJava getCevio() {
        return cevio;
    }

    public TalkerComponentStructure getStructure() {
        return structure;
    }
}