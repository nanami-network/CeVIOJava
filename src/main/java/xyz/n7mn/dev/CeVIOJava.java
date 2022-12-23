package xyz.n7mn.dev;

import com.sun.jna.Native;
import com.sun.jna.ptr.PointerByReference;
import xyz.n7mn.dev.structure.StringArrayStructure;
import xyz.n7mn.dev.structure.TalkDataStructure;

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
                System.out.println(getTalkData(v));
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
        if (!impl.IsHostStarted()) {
            throw new IllegalStateException("ホストが見つかりませんでした");
        }
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

    public void speak(TalkDataStructure talkDataStructure, String text) {
        //TODO: ..
        //impl.Speak(Native.toByteArray(text, "Shift-JIS"));

    }

    public TalkDataStructure getTalkData(String cast) {
        TalkDataStructure data = new TalkDataStructure();
        impl.getTalkData(Native.toByteArray(cast, "Shift-JIS"), data);
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
        if (!impl.IsHostStarted()) {
            throw new IllegalStateException("ホストが見つかりませんでした");
        }
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
