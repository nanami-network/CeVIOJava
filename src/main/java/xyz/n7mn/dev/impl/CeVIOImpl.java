package xyz.n7mn.dev.impl;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;
import xyz.n7mn.dev.structure.CastSettingsImpl;

public interface CeVIOImpl extends Library {
    String HostVersion();

    boolean IsHostStarted();

    int StartHost(boolean noWait);

    //
    //double GetTextDuration(Stirnng text);

    void Speak(CastSettingsImpl settings, byte[] text);

    boolean CloseHost(int value);

    boolean OutputWaveToFile(CastSettingsImpl settings, byte[] text, byte[] path);

    void SetTalker(CastSettingsImpl settings);
    void GetTalker(byte[] cast, CastSettingsImpl settings);
    //void Speak(byte[] text);

    //void getTalker(byte[] cast, byte[] pointer, Pointer array);

    //WString test(WString test);

   // int getVolume();

    int AvailableCasts(PointerByReference reference);
}