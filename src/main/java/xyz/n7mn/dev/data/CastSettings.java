package xyz.n7mn.dev.data;

import xyz.n7mn.dev.CeVIOJava;
import xyz.n7mn.dev.structure.CastSettingsStructure;

public class CastSettings {
    private final CeVIOJava cevio;
    private CastSettingsStructure structure;

    public CastSettings(CeVIOJava cevio, CastSettingsStructure structure) {
        this.cevio = cevio;
        this.structure = structure;
    }

    public String getCast() {
        return structure.cast;
    }

    public int getSpeed() {
        sync();
        return structure.speed;
    }

    public CastSettings setSpeed(int speed) {
        structure.speed = speed;
        write();
        return this;
    }

    public int getVolume() {
        sync();
        return structure.volume;
    }

    public CastSettings setVolume(int volume) {
        structure.volume = volume;
        write();
        return this;
    }

    public int getAlpha() {
        sync();
        return structure.alpha;
    }

    public CastSettings setAlpha(int alpha) {
        structure.alpha = alpha;
        write();
        return this;
    }

    public int getToneScale() {
        sync();
        return structure.toneScale;
    }

    public CastSettings setToneScale(int toneScale) {
        structure.alpha = toneScale;
        write();
        return this;
    }

    public void speak(String text, boolean wait) {
        cevio.speak(structure, text, wait);
    }

    public void speak(String text) {
        speak(text, true);
    }

    public void saveToFile(String text, String path) {
        cevio.save(structure, text, path);
    }

    public void sync() {
        this.structure = cevio.getCastSettingsImpl(structure.cast);
    }

    public void write() {
        cevio.write(structure);
    }

    public CastSettingsStructure getStructure() {
        return structure;
    }

    public CeVIOJava getCevio() {
        return cevio;
    }
}
