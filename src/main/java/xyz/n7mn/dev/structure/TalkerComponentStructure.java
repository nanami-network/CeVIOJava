package xyz.n7mn.dev.structure;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"id", "name", "value"})
public class TalkerComponentStructure extends Structure {
    public String id;
    public String name;
    public int value;

    public TalkerComponentStructure(Pointer pointer) {
        super(pointer);
        setStringEncoding("Shift-JIS");
        read();
    }

    public TalkerComponentStructure() {
        super();
        setStringEncoding("Shift-JIS");
        read();
    }

    public static class ByReference extends TalkerComponentStructure implements Structure.ByReference {
        public ByReference(Pointer pointer) {
            super(pointer);
        }
    }
}
