package world;

public class Affect {
    public enum Flags {}; // llista de flags possibles?
    public enum type {FLAG,STAT};

    private Flags flag; // pot ser un flag o un stat s'han de separar?
    private Stat stat; // pot ser un flag o un stat , s'han de separar?
    private Template origin;  // per a saber si ve de raza, especie, objecte, spell....
    private int value;
    private long timer;  // -1 per a permanent
}
