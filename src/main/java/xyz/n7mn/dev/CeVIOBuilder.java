package xyz.n7mn.dev;

import com.sun.jna.Native;
import xyz.n7mn.dev.data.CastSettings;
import xyz.n7mn.dev.data.TalkerComponent;
import xyz.n7mn.dev.data.enums.PlatformType;
import xyz.n7mn.dev.impl.CeVIOImpl;
import xyz.n7mn.dev.structure.PhonemeDataStructure;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class CeVIOBuilder {
    public static void main(String[] args) throws IOException {

    }

    private boolean autoStart;
    private PlatformType platformType = PlatformType.CEVIO_CREATIVE_STUDIO;

    public boolean isAutoStart() {
        return autoStart;
    }

    public CeVIOBuilder setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
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
            throw new IllegalStateException("Not Support Yet :(");
        }
    }
}