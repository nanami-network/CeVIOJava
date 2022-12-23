package xyz.n7mn.dev.structure;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

@Structure.FieldOrder({"name"})
public class StringArrayStructure extends Structure {
    public StringArrayStructure(Pointer pointer) {
        super(pointer);
        setStringEncoding("Shift-JIS");
        read();
    }

    public static class ByReference extends StringArrayStructure implements Structure.ByReference {
        public ByReference(Pointer pointer) {
            super(pointer);
        }
    }
    public String name;
}
