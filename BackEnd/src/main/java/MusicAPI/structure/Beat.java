package MusicAPI.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Beat implements Serializable {
    Collection<VoiceElement> notes;

    public Beat(){
    	notes = new ArrayList<VoiceElement> ();
    }

    public void addNote(Note note) {
    	notes.add(note);
    }

    public void addChord(Chord chord) {
        notes.add(chord);
    }

    public void addRest(Rest rest) {
        notes.add(rest);
    }

    public void addArpeggio(Arpeggio arpeggio) {
        notes.add(arpeggio);
    }

    public Collection<VoiceElement> getVoiceElements() {
        return notes;
    }

    public ArrayList<? extends Note> getNotes() {
        return null;
    }
}
