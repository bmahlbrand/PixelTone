package MusicAPI.harmonicsKB;

import java.util.*;

/**
 * Created by ben on 5/10/2015.
 */
public enum Mode {
    Ionian(0),
    Dorian(1),
    Phygrian(2),
    Lydian(3),
    Mixolydian(4),
    Aeolian(5),
    Locrian(6);

    private int index;
    private Interval [] steps = { Interval.Tone, Interval.Tone, Interval.Semitone, Interval.Tone, Interval.Tone,
            Interval.Tone, Interval.Semitone };

    Mode(int index) {
        this.index = index;
    }

    //returns corresponding index of mode for scale rotation
    public int getIndex() {
        return index;
    }

    public ArrayList<Interval> getSteps() {
        ArrayList<Interval> values = new ArrayList(Arrays.asList(steps));
        Collections.rotate(values, getIndex());
        return values;
    }

}
