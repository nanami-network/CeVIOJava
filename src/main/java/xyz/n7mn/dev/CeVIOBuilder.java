package xyz.n7mn.dev;

import com.sun.jna.Native;
import xyz.n7mn.dev.data.CastSettings;
import xyz.n7mn.dev.data.TalkerComponent;
import xyz.n7mn.dev.impl.CeVIOImpl;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class CeVIOBuilder {
    public static void main(String[] args) throws IOException {
        //後で実装

    }

    private boolean autoStart;

    public boolean isAutoStart() {
        return autoStart;
    }

    public CeVIOBuilder setAutoStart(boolean autoStart) {
        this.autoStart = autoStart;
        return this;
    }

    public CeVIOJava create() {
        return new CeVIOJava(Native.load("CeVIOJava.dll", CeVIOImpl.class), this);
    }
}