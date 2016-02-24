package MusicAPI.harmonicsKB;

/**
 * Created by ben on 5/10/2015.
 */
public enum Interval {
    Semitone(1),
    Tone(2),
    Tritone(3);

    private int semitones;

    Interval(int semitones) {
        this.semitones = semitones;
    }

    public int toSemitones() {
        return semitones;
    }
}
