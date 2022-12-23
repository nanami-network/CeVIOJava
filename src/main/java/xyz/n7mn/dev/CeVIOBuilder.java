package xyz.n7mn.dev;

import com.sun.jna.Native;

import java.io.File;
import java.io.IOException;

public class CeVIOBuilder {
    public static void main(String[] args) throws IOException {
        //後で実装
        new CeVIOBuilder().create();

    }

    public CeVIOJava create() {
        return new CeVIOJava(Native.load("CeVIOJava.dll", CeVIOImpl.class), this);
    }
}