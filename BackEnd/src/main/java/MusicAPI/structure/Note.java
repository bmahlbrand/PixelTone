package MusicAPI.structure;

import MusicAPI.harmonicsKB.dynamics.Accent;
import MusicAPI.harmonicsKB.dynamics.Dynamics;

import java.io.Serializable;

public class Note extends VoiceElement implements Serializable {
    Tone tone;
    Octave octave;
    Dynamics dynamics;
    Accent accent;

    //assumes tone, accidental, optional octave [A-G][#|b][0-8]
    public Note(String note) {
        this.tone = new Tone(note);
        this.dynamics = Dynamics.Forte;
        this.accent = Accent.Unaccented;
    }

    public Note(int index) {
        this.tone = Tone.fromIndex(index);
    }

    public Accidental getAccidental() {
        return tone.accidental;
    }

    public int getIndex() {
        return tone.index();
    }

    public Note getHalfStep() {
        return new Note(tone.halfStep().toString());
    }

    public Note getWholeStep() {
        return new Note(tone.wholeStep().toString());
    }

    @Override
    public String toString() {
        return tone.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!Note.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final Note other = (Note) obj;

        if (this.tone == other.tone
                && this.duration == other.duration
                && this.octave == other.octave)
            return true;
        return false;
    }

    public static void main() {
        System.out.println(new Note("A"));
    }
}