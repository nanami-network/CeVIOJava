package xyz.n7mn.dev;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import xyz.n7mn.dev.data.*;
import xyz.n7mn.dev.data.enums.HostCloseMode;
import xyz.n7mn.dev.data.enums.HostStartResult;
import xyz.n7mn.dev.impl.CeVIOImpl;
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

    public void speak(CastSettings castSettings, String text, boolean wait) {
        checkHostStarted();
        impl.Speak(castSettings.getStructure(), Native.toByteArray(text, "Shift-JIS"), wait);
    }

    /**
     * @param castSettings キャストインスタンス
     * @param text 言わせたい文字
     * @param path 保存先 (最後に .wavをつけてください！)
     * @return 結果
     */
    public boolean save(CastSettings castSettings, String text, String path) {
        checkHostStarted();
        return impl.OutputWaveToFile(castSettings.getStructure(), Native.toByteArray(text, "Shift-JIS"), Native.toByteArray(path, "Shift-JIS"));
    }

    /**
     * @param settings APIサイドと同期します
     */
    public void setCastSettings(CastSettings settings) {
        checkHostStarted();
        impl.SetTalker(settings.getStructure());
    }

    /**
     * @param cast キャスト名
     * @return キャストの設定情報
     */
    public CastSettings getCastSettings(String cast) {
        return new CastSettings(this, getCastSettingsStructure(cast));
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
        return (StringArrayStructure.ByReference[]) byReference.toArray(length);
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

    private void checkHostStarted() {
        if (!impl.IsHostStarted()) {
            throw new IllegalStateException("ホストが見つかりませんでした");
        }
    }

    /**
     * 【CeVIO AI】に終了を要求します。
     * @param mode
     * <br> - {@link HostCloseMode#DEFAULT} は安全に終了できます。
     */
    public void stop(HostCloseMode mode) {
        impl.CloseHost(mode.ordinal());
    }

    public CeVIOImpl getImpl() {
        return impl;
    }
}
