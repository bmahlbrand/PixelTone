package MusicAPI.structure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Beat implements Serializable {
    Collection<VoiceElement> notes;

    public void addNote(Note note) {

    }

    public ArrayList<? extends Note> getNotes() {
        return null;
    }
}