package xyz.n7mn.dev.impl;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;
import xyz.n7mn.dev.data.TalkerComponent;
import xyz.n7mn.dev.structure.CastSettingsStructure;
import xyz.n7mn.dev.structure.TalkerComponentStructure;

public interface CeVIOImpl extends Library {
    String HostVersion();

    boolean IsHostStarted();
    int StartHost(boolean noWait);

    void Speak(CastSettingsStructure settings, byte[] text, boolean wait);
    double GetTextDuration(CastSettingsStructure settings, byte[] text);

    boolean CloseHost(int value);

    boolean OutputWaveToFile(CastSettingsStructure settings, byte[] text, byte[] path);

    void SetTalker(CastSettingsStructure settings);
    void GetTalker(byte[] cast, CastSettingsStructure settings);

    int GetComponents(CastSettingsStructure settings, PointerByReference reference);
    void GetComponent(CastSettingsStructure settings, byte[] name, TalkerComponentStructure component);
    void SetComponent(CastSettingsStructure settings, TalkerComponentStructure component);

    int AvailableCasts(PointerByReference reference);
}