package xyz.n7mn.dev;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import xyz.n7mn.dev.data.CastSettings;
import xyz.n7mn.dev.data.TalkerComponent;
import xyz.n7mn.dev.data.enums.HostCloseMode;
import xyz.n7mn.dev.data.enums.HostStartResult;
import xyz.n7mn.dev.impl.CeVIOImpl;
import xyz.n7mn.dev.structure.CastSettingsStructure;
import xyz.n7mn.dev.structure.StringArrayStructure;
import xyz.n7mn.dev.structure.TalkerComponentStructure;

import java.util.Arrays;
import java.util.List;

public class CeVIOJava {
    private final CeVIOImpl impl;
    private final boolean checkHost;

    public CeVIOJava(CeVIOImpl impl, CeVIOBuilder builder) {
        this.impl = impl;
        if (builder.isAutoStart() && !isHostStarted()) {
            start(false);
        }
        this.checkHost = builder.isCheckHost();
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

    /**
     * @return 利用可能なキャスト
     */
    public List<String> getAvailableCastsList() {
        return Arrays.asList(getAvailableCasts());
    }

    /**
     * @return 利用可能なキャスト
     */
    //よくわからないが、 StringArrayStructureにしないと 文字が３文字しか受け取れない？？？？
    public String[] getAvailableCasts() {
        checkHostStarted();
        PointerByReference ref = new PointerByReference();
        int length = impl.AvailableCasts(ref);
        return ref.getValue().getStringArray(0, length, "Shift-JIS");
    }

    /**
     * @return 利用可能なキャスト
     */
    @Deprecated
    public StringArrayStructure[] getAvailableCastsStructure() {
        checkHostStarted();
        PointerByReference ref = new PointerByReference();
        final int length = impl.AvailableCasts(ref);
        StringArrayStructure.ByReference byReference = new StringArrayStructure.ByReference(ref.getValue());
        return (StringArrayStructure[]) byReference.toArray(length);
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
    public boolean stop(HostCloseMode mode) {
        return impl.CloseHost(mode.ordinal());
    }

    public void checkHostStarted() {
        if (checkHost && !impl.IsHostStarted()) {
            throw new IllegalStateException("ホストが見つかりませんでした");
        }
    }

    public CeVIOImpl getImpl() {
        return impl;
    }
}