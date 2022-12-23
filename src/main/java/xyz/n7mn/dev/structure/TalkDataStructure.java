package xyz.n7mn.dev.structure;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"cast", "speed", "volume", "alpha", "toneScale"})
public class TalkDataStructure extends Structure {
    public String cast;
    public int speed;
    public int volume;
    public int alpha;
    public int toneScale;

    public TalkDataStructure(Pointer pointer) {
        super(pointer);
        setStringEncoding("Shift-JIS");
        read();
    }

    public TalkDataStructure() {
        super();
        setStringEncoding("Shift-JIS");
    }

    @Override
    public String toString() {
        return "TalkDataStructure{" +
                "cast='" + cast + '\'' +
                ", speed=" + speed +
                ", volume=" + volume +
                ", alpha=" + alpha +
                ", toneScale=" + toneScale +
                '}';
    }

    /*private int Tone;
    private int ToneScale;
    private int Alpha;*/
}
