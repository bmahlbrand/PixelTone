package MusicAPI.harmonicsKB.triads;

import MusicAPI.structure.Note;
import MusicAPI.utils.LimitedQueue;
import MusicAPI.harmonicsKB.intervals.Degree;
import MusicAPI.harmonicsKB.intervals.Mode;

/**
 * Created by ben on 5/9/2015.
 */
public abstract class Triad {
    Mode mode;
    LimitedQueue<Degree> degrees;
    LimitedQueue<Note> notes;

    protected abstract LimitedQueue<Note> constructDegrees();

    protected abstract LimitedQueue<Note> constructChordTones(Note root);

    protected abstract boolean noteInTriad(Note root, Note check);
}
