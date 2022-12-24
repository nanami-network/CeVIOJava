package xyz.n7mn.dev.structure;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"phoneme", "startTime", "endTime"})
public class PhonemeDataStructure extends Structure {
    public String phoneme;
    public double startTime;
    public double endTime;

    public PhonemeDataStructure(Pointer pointer) {
        super(pointer);
        setStringEncoding("Shift-JIS");
        read();
    }

    public PhonemeDataStructure() {
        super();
        setStringEncoding("Shift-JIS");
    }

    public static class ByReference extends PhonemeDataStructure implements Structure.ByReference {
        public ByReference(Pointer pointer) {
            super(pointer);
        }
    }
}
