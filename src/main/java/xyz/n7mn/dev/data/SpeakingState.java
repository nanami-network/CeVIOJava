package xyz.n7mn.dev.data;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;
import xyz.n7mn.dev.CeVIOJava;

public class SpeakingState {
    private CeVIOJava cevio;
    private Pointer pointer;

    public SpeakingState(CeVIOJava cevio, Pointer pointer) {
        this.cevio = cevio;
        this.pointer = pointer;
    }

    public void wait(double timeOut) {

    }

    public CeVIOJava getCevio() {
        return cevio;
    }

    public Pointer getPointer() {
        return pointer;
    }
}