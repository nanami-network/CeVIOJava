package xyz.n7mn.dev.impl;

import xyz.n7mn.dev.CeVIOJava;
import xyz.n7mn.dev.structure.CastSettingsImpl;

public class CastSettings {
    private final CeVIOJava cevio;
    private final CastSettingsImpl structure;

    public CastSettings(CeVIOJava cevio, CastSettingsImpl structure) {
        this.cevio = cevio;
        this.structure = structure;
    }

    public String getCast() {
        return structure.cast;
    }

    public int getSpeed() {
        return structure.speed;
    }

    public CastSettings setSpeed(int speed) {
        structure.speed = speed;
        sync();
        return this;
    }

    public int getVolume() {
        return structure.volume;
    }

    public CastSettings setVolume(int volume) {
        structure.volume = volume;
        sync();
        return this;
    }

    public int getAlpha() {
        return structure.alpha;
    }

    public CastSettings setAlpha(int alpha) {
        structure.alpha = alpha;
        sync();
        return this;
    }

    public int getToneScale() {
        return structure.toneScale;
    }

    public CastSettings setToneScale(int toneScale) {
        structure.alpha = toneScale;
        sync();
        return this;
    }

    public void speak(String text) {
        //TODO:
    }

    public void saveToFile(String text, String path) {
        cevio.save(structure, text, path);
    }

    public void sync() {
        cevio.sync(structure);
    }

    public CastSettingsImpl getStructure() {
        return structure;
    }

    public CeVIOJava getCevio() {
        return cevio;
    }
}
