package xyz.n7mn.dev.data;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import xyz.n7mn.dev.CeVIOJava;
import xyz.n7mn.dev.structure.CastSettingsStructure;
import xyz.n7mn.dev.structure.PhonemeDataStructure;
import xyz.n7mn.dev.structure.TalkerComponentStructure;

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
        structure.toneScale = toneScale;
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

    public TalkerComponent getComponent(String name) {
        cevio.checkHostStarted();
        TalkerComponentStructure component = new TalkerComponentStructure();
        cevio.getImpl().GetComponent(structure, Native.toByteArray(name, "Shift-JIS"), component);
        return new TalkerComponent(this, component);
    }

    public TalkerComponentCollection getComponents() {
        cevio.checkHostStarted();
        sync();
        PointerByReference ref = new PointerByReference();
        final int length = cevio.getImpl().GetComponents(structure, ref);
        TalkerComponentStructure.ByReference byReference = new TalkerComponentStructure.ByReference(ref.getValue());
        return new TalkerComponentCollection(this, (TalkerComponentStructure.ByReference[]) byReference.toArray(length));
    }

    /**
     * 対象のキャストにセリフを喋らせます
     * @param text　セリフ
     * @param wait 終わるまで待たせるか（通常は true)
     */
    public SpeakingState speak(String text, boolean wait) {
        cevio.checkHostStarted();
        SpeakingState state = new SpeakingState(cevio, cevio.getImpl().Speak(structure, Native.toByteArray(text, "Shift-JIS"), true));
        if (wait) {
            state.wait(0d);
        }
        return state;
    }

    public SpeakingState speak(String text) {
        return speak(text, true);
    }

    /**
     * 指定したセリフの長さを取得します。
     * @param text セリフ
     * @return 長さ (秒)
     */
    public double getTextDuration(String text) {
        return cevio.getImpl().GetTextDuration(structure, Native.toByteArray(text, "Shift-JIS"));
    }

    public PhonemeDataStructure[] getPhonemes(String text) {
        PointerByReference ref = new PointerByReference();
        final int length = cevio.getImpl().GetPhonemes(structure, Native.toByteArray(text, "Shift-JIS"), ref);
        PhonemeDataStructure.ByReference byReference = new PhonemeDataStructure.ByReference(ref.getValue());
        return (PhonemeDataStructure[]) byReference.toArray(length);
    }

    /**
     * @param text セリフ
     * @param path 保存先 (最後に .wavをつけてください！)
     * @return 結果
     */
    public boolean saveToFile(String text, String path) {
        return cevio.getImpl().OutputWaveToFile(structure, Native.toByteArray(text, "Shift-JIS"), Native.toByteArray(path, "Shift-JIS"));
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
