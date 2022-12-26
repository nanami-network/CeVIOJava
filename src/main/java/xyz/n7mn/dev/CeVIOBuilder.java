package xyz.n7mn.dev;

import com.sun.jna.Native;
import xyz.n7mn.dev.data.CastSettings;
import xyz.n7mn.dev.data.TalkerComponent;
import xyz.n7mn.dev.data.enums.PlatformType;
import xyz.n7mn.dev.impl.CeVIOImpl;
import xyz.n7mn.dev.structure.PhonemeDataStructure;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CeVIOBuilder {
    public static void main(String[] args) throws IOException {
        //後で実装
        CeVIOJava cevio = new CeVIOBuilder()
                .setAutoStart(true)
                .setPlatformType(PlatformType.CEVIO_CREATIVE_STUDIO)
                .create();

        cevio.start(false);

        long start = System.currentTimeMillis();
        Arrays.stream(cevio.getAvailableCastsStructure()).forEach(v -> {
            System.out.println(v.name);
        });

        List<String> availableCasts = cevio.getAvailableCastsList();
        if (availableCasts.isEmpty()) {
            System.err.println("Non AvailableCasts");
        } else {

            CastSettings settings = cevio.getCastSettings(availableCasts.get(0));
            for (TalkerComponent component : settings.getComponents().getAsCollection()) {
                final int random = new Random().nextInt(100);

                System.out.printf("name=%s id=%s value=%s newValue=%s%n", component.getName(), component.getId(), component.getValue(), random);
                component.setValue(random);
            }
            for (TalkerComponent component : settings.getComponents().getAsCollection()) {
                System.out.printf("[Log] name=%s id=%s value=%s%n", component.getName(), component.getId(), component.getValue());
            }
            //for (PhonemeDataStructure phonemeDataStructure : settings.getPhonemes("こんにちは！" + settings.getCast() + "です！")) {
            //    System.out.println(phonemeDataStructure.phoneme + " s:" + phonemeDataStructure.startTime + " e:" + phonemeDataStructure.endTime);
            //}
            System.out.printf("lag:" + (System.currentTimeMillis() - start));

            settings.speak("こんにちは！" + settings.getCast() + "です！", true);
            //[Log] name=Dark id=CTNV-JPF-FP4-04 value=10
            //[Log] name=Normal id=CTNV-JPF-FP4-02 value=2
            //[Log] name=Bright id=CTNV-JPF-FP4-01 value=93
            //[Log] name=Strong id=CTNV-JPF-FP4-03 value=84

            //[Log] name=Dark id=CTNV-JPF-FP4-04 value=9
            //[Log] name=Normal id=CTNV-JPF-FP4-02 value=92
            //[Log] name=Bright id=CTNV-JPF-FP4-01 value=84
            //[Log] name=Strong id=CTNV-JPF-FP4-03 value=24

            //[Log] name=Dark id=CTNV-JPF-FP4-04 value=13
            //[Log] name=Normal id=CTNV-JPF-FP4-02 value=33
            //[Log] name=Bright id=CTNV-JPF-FP4-01 value=48
            //[Log] name=Strong id=CTNV-JPF-FP4-03 value=19

        }
        //cevio.stop();
    }

    private boolean autoStart, checkHost;
    private PlatformType platformType = PlatformType.CEVIO_CREATIVE_STUDIO;

    public boolean isAutoStart() {
        return autoStart;
    }

    public CeVIOBuilder setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
        return this;
    }

    public boolean isCheckHost() {
        return checkHost;
    }

    public CeVIOBuilder setCheckHost(boolean checkHost) {
        this.checkHost = checkHost;
        return this;
    }

    public PlatformType getPlatformType() {
        return platformType;
    }

    public CeVIOBuilder setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
        return this;
    }

    public CeVIOJava create() {
        if (platformType == PlatformType.CEVIO_CREATIVE_STUDIO) {
            return new CeVIOJava(Native.load("CeVIOJavaCreative.dll", CeVIOImpl.class), this);
        } else {
            return new CeVIOJava(Native.load("CeVIOJavaAI.dll", CeVIOImpl.class), this);
        }
    }
}