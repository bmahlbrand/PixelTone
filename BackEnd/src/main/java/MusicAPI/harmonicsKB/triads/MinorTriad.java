package MusicAPI.harmonicsKB.triads;

import MusicAPI.harmonicsKB.scale.DiatonicScale;
import MusicAPI.structure.Note;
import MusicAPI.utils.LimitedQueue;
import MusicAPI.harmonicsKB.intervals.Degree;
import MusicAPI.harmonicsKB.intervals.Mode;

/**
 * Created by ben on 5/10/2015.
 */
public class MinorTriad extends Triad {

    public MinorTriad(Note root) {
        mode = Mode.Aeolian;
        degrees = constructDegrees();
        notes = constructChordTones(root);
    }

    @Override
    protected LimitedQueue constructDegrees() {
        LimitedQueue<Degree> degrees = new LimitedQueue<>(3);
        degrees.add(Degree.Tonic);
        degrees.add(Degree.Mediant);
        degrees.add(Degree.Dominant);
        return degrees;
    }

    @Override
    public LimitedQueue<Note> constructChordTones(Note root) {
        LimitedQueue<Note> notes = new LimitedQueue<>(3);
        DiatonicScale scale = new DiatonicScale(root, mode);

        for (Degree d : degrees) {
            notes.add(scale.getNote(d));
        }

        return notes;
    }

    @Override
    public boolean noteInTriad(Note root, Note check) {
        MinorTriad triad = new MinorTriad(root);

        for (Note note : triad.notes) {
            if (note == check)
                return true;
        }

        return false;
    }
}
