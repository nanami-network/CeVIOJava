package xyz.n7mn.dev;

import com.sun.jna.Structure;

@Structure.FieldOrder({"Cast", "Volume", "ToneScale", "Speed"})
public class Talker extends Structure {
    private String Cast;
    private int Speed;
    private int Volume;
    private int Tone;
    private int ToneScale;
    private int Alpha;
}
