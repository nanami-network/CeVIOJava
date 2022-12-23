package xyz.n7mn.dev.structure;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"id", "name", "value"})
public class TalkerComponent extends Structure {
    public String id;
    public String name;
    public int value;

    public TalkerComponent(Pointer pointer) {
        super(pointer);
        setStringEncoding("Shift-JIS");
        read();
    }

    public static class ByReference extends TalkerComponent implements Structure.ByReference {
        public ByReference(Pointer pointer) {
            super(pointer);
        }
    }
}
