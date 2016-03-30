package MusicAPI.harmonicsKB.triads;

import MusicAPI.harmonicsKB.voicing.Inversion;
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

    public LimitedQueue<Note> getNotes() {
        return notes;
    }

    protected abstract boolean noteInTriad(Note root, Note check);

    public boolean noteInTriad(Note check){
         for (Note note : notes) {
            if (note.equalsIgnoreOctaveAndDuration(check))
                return true;
        }

        return false;
    }

    void inversion(Inversion inversion) {
        switch (inversion) {
            case Root:
                break;
            case First:
                try {
                    notes.get(0).getOctave().stepUp();
                } catch(Exception e) { e.printStackTrace(); }

                break;
            case Second:
                try {
                    notes.get(0).getOctave().stepUp();
                } catch(Exception e) { e.printStackTrace(); }

                try {
                    notes.get(1).getOctave().stepUp();
                } catch(Exception e) { e.printStackTrace(); }

                break;
        }
    }
}
