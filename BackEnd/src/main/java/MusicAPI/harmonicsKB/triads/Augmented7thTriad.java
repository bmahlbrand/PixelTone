package MusicAPI.harmonicsKB.triads;

import MusicAPI.harmonicsKB.Degree;
import MusicAPI.harmonicsKB.Mode;
import MusicAPI.harmonicsKB.scale.DiatonicScale;
import MusicAPI.structure.Note;
import MusicAPI.utils.LimitedQueue;

/**
 * Created by ben on 5/10/2015.
 */
public class Augmented7thTriad extends Triad {
    public Augmented7thTriad(Note root) {
        mode = Mode.Lydian;  // TODO verify this mapping is indeed correct
        degrees = constructDegrees();
        notes = constructChordTones(root);
    }

    @Override
    protected LimitedQueue constructDegrees() {
        LimitedQueue<Degree> degrees = new LimitedQueue<>(4);
        degrees.add(Degree.Tonic);
        degrees.add(Degree.Mediant);
        degrees.add(Degree.Dominant);
        degrees.add(Degree.Leading);
        return degrees;
    }

    @Override
    public LimitedQueue<Note> constructChordTones(Note root) {
        LimitedQueue<Note> notes = new LimitedQueue<>(4);
        DiatonicScale scale = new DiatonicScale(root, mode);

        for (Degree d : degrees) {
            notes.add(scale.getNote(d));
        }

        return notes;
    }

    @Override
    public boolean noteInTriad(Note root, Note check) {
        Augmented7thTriad triad = new Augmented7thTriad(root);

        for (Note note : triad.notes) {
            if (note == check)
                return true;
        }

        return false;
    }
}
