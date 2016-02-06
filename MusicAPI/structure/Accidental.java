package MusicAPI.structure;

public enum Accidental {
    Flat(-1),
    Natural(0),
    Sharp(1);

    private int _accidental;

    Accidental(int accidental) {
        _accidental = accidental;
    }

    public int toInt() {
        return _accidental;
    }
}