package xyz.n7mn.dev;

import com.sun.jna.*;
import com.sun.jna.ptr.PointerByReference;

import java.awt.*;

public interface CeVIOImpl extends Library {

    String HostVersion();

    boolean IsHostStarted();

    int StartHost(boolean noWait);

    //
    //double GetTextDuration(String text);

    boolean CloseHost(int value);

    //void Speak(byte[] text);

    //void getTalker(byte[] cast, byte[] pointer, Pointer array);

    //WString test(WString test);

   // int getVolume();

    int AvailableCasts(PointerByReference reference);
}
