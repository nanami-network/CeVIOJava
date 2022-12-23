package xyz.n7mn.dev;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.StringArray;
import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;

import java.sql.Struct;

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
