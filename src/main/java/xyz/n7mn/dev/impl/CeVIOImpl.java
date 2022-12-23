package xyz.n7mn.dev.impl;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;
import xyz.n7mn.dev.structure.CastSettingsImpl;

public interface CeVIOImpl extends Library {
    String HostVersion();

    boolean IsHostStarted();
    int StartHost(boolean noWait);

    void Speak(CastSettingsImpl settings, byte[] text, boolean wait);

    boolean CloseHost(int value);

    boolean OutputWaveToFile(CastSettingsImpl settings, byte[] text, byte[] path);

    void SetTalker(CastSettingsImpl settings);
    void GetTalker(byte[] cast, CastSettingsImpl settings);

    int AvailableCasts(PointerByReference reference);
}