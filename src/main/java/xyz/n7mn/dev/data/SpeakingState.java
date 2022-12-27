package xyz.n7mn.dev.data;

import com.sun.jna.Pointer;
import xyz.n7mn.dev.CeVIOJava;

public class SpeakingState {
    private final CeVIOJava cevio;
    private final Pointer state;

    public SpeakingState(CeVIOJava cevio, Pointer state) {
        this.cevio = cevio;
        this.state = state;
    }

    public void wait(double timeOut) {
        cevio.getImpl().Wait(state, timeOut);
    }

    public boolean isCompleted() {
        return cevio.getImpl().IsSpeakingCompleted(state);
    }

    public boolean isSucceeded() {
        return cevio.getImpl().IsSpeakingCompleted(state);
    }

    public CeVIOJava getCevio() {
        return cevio;
    }

    public Pointer getState() {
        return state;
    }

    public void clean() {
        state.clear(0);
    }
}