package xyz.n7mn.dev.data;

import xyz.n7mn.dev.CeVIOJava;
import xyz.n7mn.dev.structure.CastSettingsStructure;
import xyz.n7mn.dev.structure.PhonemeDataStructure;

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

    public int getVolume() {
        sync();
        return structure.volume;
    }

    public int getCacheVolume() {
        return structure.volume;
    }

    public CastSettings setVolume(int volume) {
        structure.volume = volume;
        write();
        return this;
    }

    public int getSpeed() {
        sync();
        return structure.speed;
    }

    public int getCacheSpeed() {
        return structure.speed;
    }

    public CastSettings setSpeed(int speed) {
        structure.speed = speed;
        write();
        return this;
    }

    public int getTone() {
        sync();
        return structure.tone;
    }

    public int getCacheTone() {
        return structure.tone;
    }

    public CastSettings setTone(int tone) {
        structure.tone = tone;
        write();
        return this;
    }

    public int getAlpha() {
        sync();
        return structure.alpha;
    }

    public int getCacheAlpha() {
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

    public int getCacheToneScale() {
        return structure.toneScale;
    }

    public CastSettings setToneScale(int toneScale) {
        structure.alpha = toneScale;
        write();
        return this;
    }

    public CastSettings setOnce(int volume, int speed, int tone, int alpha, int toneScale) {
        structure.volume = volume;
        structure.speed = speed;
        structure.tone = tone;
        structure.alpha = alpha;
        structure.toneScale = toneScale;
        write();
        return this;
    }

    public TalkerComponentCollection getComponents() {
        sync();
        return cevio.getTalkerComponents(this);
    }

    public void speak(String text, boolean wait) {
        cevio.speak(this, text, wait);
    }

    public void speak(String text) {
        speak(text, true);
    }

    /**
     * 指定したセリフの長さを取得します。
     * @param text セリフ
     * @return 長さ (秒)
     */
    public double getTextDuration(String text) {
        return cevio.getTextDuration(this, text);
    }

    public PhonemeDataStructure[] getPhonemes(String text) {
        return cevio.getPhonemes(this, text);
    }

    public boolean saveToFile(String text, String path) {
        return cevio.save(this, text, path);
    }

    public void sync() {
        this.structure = cevio.getCastSettingsStructure(structure.cast);
    }

    public void write() {
        cevio.setCastSettings(this);
    }

    public CastSettingsStructure getStructure() {
        return structure;
    }

    public CeVIOJava getCevio() {
        return cevio;
    }
}
