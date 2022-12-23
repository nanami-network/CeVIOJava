package xyz.n7mn.dev;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import xyz.n7mn.dev.impl.CastSettings;
import xyz.n7mn.dev.impl.CeVIOImpl;
import xyz.n7mn.dev.structure.StringArrayStructure;
import xyz.n7mn.dev.structure.CastSettingsImpl;
import xyz.n7mn.dev.structure.TalkerComponent;

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
                TalkerComponent[] component = getCastComponent(structure.getStructure());
                for (TalkerComponent component1 : component) {
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


    public void speak(CastSettingsImpl castSettingsImpl, String text, boolean wait) {
        checkHostStarted();
        impl.Speak(castSettingsImpl, Native.toByteArray(text, "Shift-JIS"), wait);
    }

    /**
     * @param castSettingsImpl ストラクチャー
     * @param text 言わせたい文字
     * @param path 保存先 (最後に .wavをつけてください！)
     * @return 結果
     */
    public boolean save(CastSettingsImpl castSettingsImpl, String text, String path) {
        checkHostStarted();
        return impl.OutputWaveToFile(castSettingsImpl, Native.toByteArray(text, "Shift-JIS"), Native.toByteArray(path, "Shift-JIS"));
    }

    /**
     * @param structure APIサイドと同期します
     */
    public void write(CastSettingsImpl structure) {
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
    public CastSettingsImpl getCastSettingsImpl(String cast) {
        checkHostStarted();
        CastSettingsImpl data = new CastSettingsImpl();
        impl.GetTalker(Native.toByteArray(cast, "Shift-JIS"), data);
        return data;
    }

    /**
     * @param settings キャストデータ
     * @return タルカーコンポーネント
     */
    public TalkerComponent[] getCastComponent(CastSettingsImpl settings) {
        checkHostStarted();
        PointerByReference ref = new PointerByReference();
        final int length = impl.GetComponents(settings, ref);
        TalkerComponent.ByReference byReference = new TalkerComponent.ByReference(ref.getValue());
        return (TalkerComponent.ByReference[]) byReference.toArray(length);
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
