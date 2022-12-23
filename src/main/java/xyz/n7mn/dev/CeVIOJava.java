package xyz.n7mn.dev;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import xyz.n7mn.dev.data.CastSettings;
import xyz.n7mn.dev.data.TalkerComponent;
import xyz.n7mn.dev.impl.CeVIOImpl;
import xyz.n7mn.dev.structure.StringArrayStructure;
import xyz.n7mn.dev.structure.CastSettingsStructure;
import xyz.n7mn.dev.structure.TalkerComponentStructure;

import java.util.ArrayList;
import java.util.List;

public class CeVIOJava {
    private final CeVIOImpl impl;

    public CeVIOJava(CeVIOImpl impl, CeVIOBuilder builder) {
        this.impl = impl;

        try {

            //start(false);

            long start = System.currentTimeMillis();
            getAvailableCastsList().forEach(v -> {
                System.out.println(v);
                CastSettings structure = getCastSettings(v);
                structure.setVolume(100);
                write(structure.getStructure());
            });

            getAvailableCastsList().forEach(v -> {
                System.out.println(v);
                CastSettings structure = getCastSettings(v);
                System.out.println(structure);
                structure.speak("あいうえお！");
                structure.saveToFile("あいうえお！！", "C:\\Users\\rin11\\Documents\\GitHub\\GachaPlugin\\CeVIOJavaGitHub\\Cpa" + structure.getCast() +  ".wav");
                TalkerComponentStructure[] component = getCastComponents(structure.getStructure());
                for (TalkerComponentStructure component1 : component) {
                    System.out.println(component1.id);
                    System.out.println(component1.name);
                    System.out.println(component1.value);
                }
            });
            System.out.println("lag:" + (System.currentTimeMillis() - start));
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            //stop();
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


    public void speak(CastSettingsStructure castSettingsStructure, String text, boolean wait) {
        checkHostStarted();
        impl.Speak(castSettingsStructure, Native.toByteArray(text, "Shift-JIS"), wait);
    }

    /**
     * @param castSettingsStructure ストラクチャー
     * @param text 言わせたい文字
     * @param path 保存先 (最後に .wavをつけてください！)
     * @return 結果
     */
    public boolean save(CastSettingsStructure castSettingsStructure, String text, String path) {
        checkHostStarted();
        return impl.OutputWaveToFile(castSettingsStructure, Native.toByteArray(text, "Shift-JIS"), Native.toByteArray(path, "Shift-JIS"));
    }

    /**
     * @param structure APIサイドと同期します
     */
    public void write(CastSettingsStructure structure) {
        checkHostStarted();
        impl.SetTalker(structure);
    }

    /**
     * @param cast キャスト名
     * @return キャストの設定情報
     */
    public CastSettings getCastSettings(String cast) {
        return new CastSettings(this, getCastSettingsImpl(cast));
    }

    /**
     * @param cast キャスト名
     * @return キャストの情報
     */
    public CastSettingsStructure getCastSettingsImpl(String cast) {
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
    public TalkerComponentStructure[] getCastComponents(CastSettingsStructure settings) {
        checkHostStarted();
        PointerByReference ref = new PointerByReference();
        final int length = impl.GetComponents(settings, ref);
        TalkerComponentStructure.ByReference byReference = new TalkerComponentStructure.ByReference(ref.getValue());
        return (TalkerComponentStructure.ByReference[]) byReference.toArray(length);
    }

    public void setCastComponent(CastSettingsStructure settings, TalkerComponentStructure component) {
        checkHostStarted();
        impl.SetComponent(settings, component);
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
