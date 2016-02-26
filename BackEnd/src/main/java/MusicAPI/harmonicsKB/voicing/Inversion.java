package MusicAPI.harmonicsKB.voicing;

public enum Inversion {
    Root(0),
    First(1),
    Second(2);

    private int inversion;

    Inversion(int inversion) {
        this.inversion = inversion;
    }
}