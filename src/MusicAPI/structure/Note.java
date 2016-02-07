package MusicAPI.structure;

import java.io.Serializable;

public class Note extends VoiceElement implements Serializable {
    Tone tone;
    int duration;
    Octave octave;

    //assumes tone, accidental, optional octave [A-G][#|b][0-8]
    public Note(String note) {
        this.tone = Tone.valueOf(Character.toString(note.charAt(0)));
    }

    public Accidental getAccidental() {
        return tone.accidental;
    }

    public static void main() {
        System.out.println(new Note("A"));
    }
}