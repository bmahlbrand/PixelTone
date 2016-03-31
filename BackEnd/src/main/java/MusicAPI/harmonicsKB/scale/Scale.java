package MusicAPI.harmonicsKB.scale;

import MusicAPI.harmonicsKB.intervals.Degree;
import MusicAPI.harmonicsKB.intervals.Interval;
import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.structure.Note;
import MusicAPI.utils.LimitedQueue;

public abstract class Scale {
    Note root;
    LimitedQueue<Note> scale;
    Mode mode;
    Interval intervals[] = null;

    Scale(Note root, Mode mode) {
        this.root = root;
        this.mode = mode;
        this.intervals = buildIntervals();
        this.scale = buildScale();
    }

    public LimitedQueue<Note> getScale() {
        return scale;
    }

    public Note getNote(Degree degree) {
        return this.scale.get(degree.toInt());
    }

    public LimitedQueue<Note> getScaleAsString() {
        if (this.scale == null) {
            this.buildScale();
        }

        return this.scale;
    }

    protected abstract LimitedQueue<Note> buildScale();

    protected abstract Interval[] buildIntervals();

    public boolean noteInScale(Note root, Note check) {
        for (Note n : scale) {
            if (n == check)
                return true;
        }

        return false;
    }

    public int noteIndex(Note root, Note check) {
        for (int i = 0; i < scale.size(); i++) {
            if (scale.get(i) == check)
                return i;
        }

        return -1;
    }

    public Degree degreeIndex(Note root, Note check) {
        for (int i = 0; i < scale.size(); i++) {
            if (scale.get(i) == check)
                return Degree.values()[i];
        }

        return Degree.NonScaleDegree;
    }
}