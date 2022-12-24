package xyz.n7mn.dev;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import xyz.n7mn.dev.data.*;
import xyz.n7mn.dev.data.enums.HostCloseMode;
import xyz.n7mn.dev.data.enums.HostStartResult;
import xyz.n7mn.dev.impl.CeVIOImpl;
import xyz.n7mn.dev.structure.PhonemeDataStructure;
import xyz.n7mn.dev.structure.StringArrayStructure;
import xyz.n7mn.dev.structure.CastSettingsStructure;
import xyz.n7mn.dev.structure.TalkerComponentStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CeVIOJava {
    private final CeVIOImpl impl;

    public CeVIOJava(CeVIOImpl impl, CeVIOBuilder builder) {
        this.impl = impl;
        if (builder.isAutoStart() && !isHostStarted()) {
            start(false);
        }
    }

    /**
     * @return ホストが起動しているか
     */
    public boolean isHostStarted() {
        return impl.IsHostStarted();
    }

    /**
     * @return ホストバージョン
     * @implNote
     * ホストが起動していない場合、エラーが発生します
     */
    public String getVersion() {
        checkHostStarted();
        return impl.HostVersion();
    }

    /**
     * @param noWait
     * <br>trueは起動のみ行います。アクセス可能かどうかはIsHostStartedで確認します。
     * <br>falseは起動後に外部からアクセス可能になるまで制御を戻しません。
     * @return 結果
     */
    public HostStartResult start(boolean noWait) {
        return HostStartResult.getByOriginal(impl.StartHost(noWait));
    }

    /**
     * CeVIOに喋ってもらいます
     * <br>通常は {@link CastSettings#speak(String)} から使用してください
     * @param castSettings インスタンス
     * @param text　セリフ
     * @param wait 終わるまで待たせるか（通常は true)
     */
    public void speak(CastSettings castSettings, String text, boolean wait) {
        checkHostStarted();
        impl.Speak(castSettings.getStructure(), Native.toByteArray(text, "Shift-JIS"), wait);
    }


    /**
     * 指定したセリフの長さを取得します。
     * <br>通常は {@link CastSettings#getTextDuration(String)} から使用してください
     * @param castSettings インスタンス
     * @param text　セリフ
     * @return 長さ (秒)
     */
    public double getTextDuration(CastSettings castSettings, String text) {
        return impl.GetTextDuration(castSettings.getStructure(), Native.toByteArray(text, "Shift-JIS"));
    }

    /**
     *
     * @param castSettings キャストインスタンス
     * @param text セリフ
     * @param path 保存先 (最後に .wavをつけてください！)
     * @return 結果
     */
    public boolean save(CastSettings castSettings, String text, String path) {
        checkHostStarted();
        return impl.OutputWaveToFile(castSettings.getStructure(), Native.toByteArray(text, "Shift-JIS"), Native.toByteArray(path, "Shift-JIS"));
    }

    /**
     * @param cast キャスト名
     * @return キャストの設定情報
     */
    public CastSettings getCastSettings(String cast) {
        return new CastSettings(this, getCastSettingsStructure(cast));
    }

    /**
     * APIサイドに {@link CastSettings} を書き込みます
     * @param settings インスタンス
     */
    public void setCastSettings(CastSettings settings) {
        checkHostStarted();
        impl.SetTalker(settings.getStructure());
    }

    /**
     * @param cast キャスト名
     * @return キャストの情報
     */
    public CastSettingsStructure getCastSettingsStructure(String cast) {
        checkHostStarted();
        CastSettingsStructure data = new CastSettingsStructure();
        impl.GetTalker(Native.toByteArray(cast, "Shift-JIS"), data);
        return data;
    }

    public PhonemeDataStructure[] getPhonemes(CastSettings settings, String text) {
        PointerByReference ref = new PointerByReference();
        final int length = impl.GetPhonemes(settings.getStructure(), Native.toByteArray(text, "Shift-JIS"), ref);
        PhonemeDataStructure.ByReference byReference = new PhonemeDataStructure.ByReference(ref.getValue());
        return (PhonemeDataStructure[]) byReference.toArray(length);
    }

    /**
     * @return 利用可能なキャスト
     */
    public List<String> getAvailableCastsList() {
        StringArrayStructure[] structure = getAvailableCastsStructure();

        List<String> casts = new ArrayList<>(structure.length);
        for (StringArrayStructure struct : structure) {
            casts.add(struct.name);
        }
        return casts;
    }

    /**
     * @return 利用可能なキャスト
     */
    public String[] getAvailableCasts() {
        StringArrayStructure[] structure = getAvailableCastsStructure();

        String[] casts = new String[structure.length];
        for (int i = 0; i < structure.length; i++) {
            casts[i] = structure[i].name;
        }
        return casts;
    }

    /**
     * @return 利用可能なキャスト
     */
    public StringArrayStructure[] getAvailableCastsStructure() {
        checkHostStarted();
        PointerByReference ref = new PointerByReference();
        final int length = impl.AvailableCasts(ref);
        StringArrayStructure.ByReference byReference = new StringArrayStructure.ByReference(ref.getValue());
        return (StringArrayStructure[]) byReference.toArray(length);
    }

    /**
     * @param settings キャストデータ
     * @return タルカーコンポーネント
     */
    public TalkerComponentCollection getTalkerComponents(CastSettings settings) {
        checkHostStarted();
        PointerByReference ref = new PointerByReference();
        final int length = impl.GetComponents(settings.getStructure(), ref);
        TalkerComponentStructure.ByReference byReference = new TalkerComponentStructure.ByReference(ref.getValue());
        return new TalkerComponentCollection(settings, (TalkerComponentStructure.ByReference[]) byReference.toArray(length));
    }

    public TalkerComponent getTalkerComponent(CastSettings settings, String name) {
        return new TalkerComponent(settings, getTalkerComponentStructure(settings, name));
    }

    public TalkerComponentStructure getTalkerComponentStructure(CastSettings settings, String name) {
        checkHostStarted();
        TalkerComponentStructure structure = new TalkerComponentStructure();
        impl.GetComponent(settings.getStructure(), Native.toByteArray(name, "Shift-JIS"), structure);
        return structure;
    }

    public void setTalkerComponent(CastSettings settings, TalkerComponent component) {
        checkHostStarted();
        impl.SetComponent(settings.getStructure(), component.getStructure());
    }

    @Deprecated
    public void setTalkerComponentStructure(CastSettings settings, TalkerComponentStructure component) {
        checkHostStarted();
        impl.SetComponent(settings.getStructure(), component);
    }

    /**
     * 【CeVIO AI】に終了を要求します。
     */
    public void stop() {
        impl.CloseHost(0);
    }

    /**
     * 【CeVIO AI】に終了を要求します。
     * @param mode
     * <br> - {@link HostCloseMode#DEFAULT} は安全に終了できます。
     */
    public void stop(HostCloseMode mode) {
        impl.CloseHost(mode.ordinal());
    }

    private void checkHostStarted() {
        if (!impl.IsHostStarted()) {
            throw new IllegalStateException("ホストが見つかりませんでした");
        }
    }

    public CeVIOImpl getImpl() {
        return impl;
    }
}