package xyz.n7mn.dev;

import com.sun.jna.Native;
import xyz.n7mn.dev.data.enums.PlatformType;
import xyz.n7mn.dev.impl.CeVIOImpl;

public class CeVIOBuilder {
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