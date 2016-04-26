package MusicAPI.harmonicsKB.intervals;

import MusicAPI.harmonicsKB.intervals.Interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by ben on 5/10/2015.
 */
public enum Mode {
    Ionian(0),
    Dorian(1),
    Phrygian(2),
    Lydian(3),
    Mixolydian(4),
    Aeolian(5),
    Locrian(6);

    private int index;
    private Interval[] steps = {Interval.Tone, Interval.Tone, Interval.Semitone, Interval.Tone, Interval.Tone,
            Interval.Tone, Interval.Semitone};

    Mode(int index) {
        this.index = index;
    }

    //returns corresponding index of mode for scale rotation
    public int getIndex() {
        return index;
    }

    public Interval[] getSteps() {
        ArrayList<Interval> values = new ArrayList(Arrays.asList(steps));

        Collections.rotate(values, -getIndex());
        Interval[] stepsCopy = {Interval.Tone, Interval.Tone, Interval.Semitone, Interval.Tone, Interval.Tone,
            Interval.Tone, Interval.Semitone};
        stepsCopy = values.toArray(stepsCopy);

        return stepsCopy;
    }

    public Mode relativeMinor() {//TODO FINISH
        switch(this) {
            case Ionian:
                return Aeolian;
        }

        System.err.println("not a major mode, invalid");
        return this;
    }

    public Mode relativeMajor() {//TODO FINISH
        switch(this) {
            case Aeolian:
                return Ionian;
        }

        System.err.println("not a minor mode, invalid");
        return this;
    }
}
