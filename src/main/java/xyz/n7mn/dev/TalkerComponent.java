package xyz.n7mn.dev;

import com.sun.jna.Structure;

@Structure.FieldOrder({"Id", "Name", "Value"})
public class TalkerComponent extends Structure {
    public String Id;
    public String Name;
    public int Value;

    public TalkerComponent() {
        super(1);
    }
}
