package MusicAPI.harmonicsKB.triads;

import MusicAPI.harmonicsKB.scale.DiatonicScale;
import MusicAPI.structure.Note;
import MusicAPI.utils.LimitedQueue;
import MusicAPI.harmonicsKB.Degree;
import MusicAPI.harmonicsKB.Mode;

/**
 * Created by ben on 5/10/2015.
 */
public class Major7ThTriad extends Triad {

    public Major7ThTriad(Note root) {
        mode = Mode.Ionian;
        degrees.add(Degree.Leading);
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
        LimitedQueue<Note> notes = new LimitedQueue<>(3);
        DiatonicScale scale = new DiatonicScale(root, mode);

        for (Degree d : degrees) {
            notes.add(scale.getNote(d));
        }

        return notes;
    }
}