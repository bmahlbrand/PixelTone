package MusicAPI.harmonicsKB;

/**
 * Created by ben on 4/26/2015.
 */
public enum HarmonicMotion {
    Parallel(0),
    Similar(1),
    Contrary(2),
    Oblique(3);

    private int value;

    HarmonicMotion(int value) {
        this.value = value;
    }

    public int toInt() { return value; }
}
