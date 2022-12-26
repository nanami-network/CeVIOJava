package xyz.n7mn.dev.structure;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"cast", "volume", "speed", "tone" ,"alpha", "toneScale"})
public class CastSettingsStructure extends Structure {
    public String cast;
    public int volume;
    public int speed;
    public int tone;
    public int alpha;
    public int toneScale;

    public CastSettingsStructure(Pointer pointer) {
        super(pointer);
        setStringEncoding("Shift-JIS");
        read();
    }

    public CastSettingsStructure() {
        super();
        setStringEncoding("Shift-JIS");
    }

    @Override
    public String toString() {
        return "CastSettingsStructure{" +
                "cast='" + cast + '\'' +
                ", volume=" + volume +
                ", speed=" + speed +
                ", tone=" + tone +
                ", alpha=" + alpha +
                ", toneScale=" + toneScale +
                '}';
    }
}
