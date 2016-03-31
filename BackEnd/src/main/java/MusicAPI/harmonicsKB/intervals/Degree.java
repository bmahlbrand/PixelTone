package MusicAPI.harmonicsKB.intervals;

import MusicAPI.harmonicsKB.scale.DiatonicScale;
import MusicAPI.structure.Note;
import MusicAPI.structure.Tone;

/**
 * Created by ben on 4/3/2015.
 */

//scale degrees used by scale/triads building functions
public enum Degree {
    //    NonScaleDegree(-1),
    Tonic(0),
    Supertonic(1),
    Mediant(2),
    Subdominant(3),
    Dominant(4),
    Submediant(5),
    Leading(6),
    NonScaleDegree(7);

    private int degree;
    private Tone nonScaleTone;

    //    Degree(String tone) { this.nonScaleTone = tone; }
    Degree(int degree) {
        this.degree = degree;
    }

    public int toInt() {
        return this.degree;
    }

    public void setNonScaleDegree(Tone note) {
        nonScaleTone = note;
    }

    public Tone getNonScaleTone() {
        return nonScaleTone;
    }

    public Degree getTonic() {
        switch (degree) {
            case 0:
                return Tonic;
            case 1:
                return Supertonic;
            case 2:
                return Mediant;
            case 3:
                return Subdominant;
            case 4:
                return Dominant;
            case 5:
                return Submediant;
            case 6:
                return Leading;
        }

        return Tonic;
    }

    public Degree getSupertonic() {
        switch (degree) {
            case 0:
                return Supertonic;
            case 1:
                return Mediant;
            case 2:
                return Subdominant;
            case 3:
                return Dominant;
            case 4:
                return Submediant;
            case 5:
                return Leading;
            case 6:
                return Tonic;
        }

        return Supertonic;
    }

    public Degree getMediant() {
        switch (degree) {
            case 0:
                return Mediant;
            case 1:
                return Subdominant;
            case 2:
                return Dominant;
            case 3:
                return Submediant;
            case 4:
                return Leading;
            case 5:
                return Tonic;
            case 6:
                return Supertonic;
        }

        return Mediant;
    }

    public Degree getSubdominant() {
        switch (degree) {
            case 0:
                return Subdominant;
            case 1:
                return Dominant;
            case 2:
                return Submediant;
            case 3:
                return Leading;
            case 4:
                return Tonic;
            case 5:
                return Supertonic;
            case 6:
                return Mediant;
        }

        return Subdominant;
    }

    public Degree getDominant() {
        switch (degree) {
            case 0:
                return Dominant;
            case 1:
                return Submediant;
            case 2:
                return Leading;
            case 3:
                return Tonic;
            case 4:
                return Supertonic;
            case 5:
                return Mediant;
            case 6:
                return Subdominant;
        }

        return Dominant;
    }

    public Degree getSubmediant() {
        switch (degree) {
            case 0:
                return Submediant;
            case 1:
                return Leading;
            case 2:
                return Tonic;
            case 3:
                return Supertonic;
            case 4:
                return Mediant;
            case 5:
                return Subdominant;
            case 6:
                return Dominant;
        }

        return Submediant;
    }

    public Degree getLeading() {
        switch (degree) {
            case 0:
                return Leading;
            case 1:
                return Tonic;
            case 2:
                return Supertonic;
            case 3:
                return Mediant;
            case 4:
                return Subdominant;
            case 5:
                return Dominant;
            case 6:
                return Submediant;
        }

        return Leading;
    }

    public static Degree numToDegree(int i) {
        switch (i) {
            default:
            case 0:
                return Degree.Tonic;
            case 1:
                return Degree.Supertonic;
            case 2:
                return Degree.Mediant;
            case 3:
                return Degree.Subdominant;
            case 4:
                return Degree.Dominant;
            case 5:
                return Degree.Submediant;
            case 6:
                return Degree.Leading;
        }
    }

    public static Degree getDegreeIndex(Note root, Mode mode, Note check, boolean sharp) {
        DiatonicScale ds = new DiatonicScale(root, mode);

        int i = ds.noteIndex(root, check);
        if (i == -1)
            return NonScaleDegree;

        return Degree.values()[i];
    }
}
