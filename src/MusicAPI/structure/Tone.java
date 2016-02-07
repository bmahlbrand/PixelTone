package MusicAPI.structure;

import java.io.Serializable;

public enum Tone implements Serializable {
    A('A'),
    B('B'),
    C('C'),
    D('D'),
    E('E'),
    F('F'),
    G('G');

    char tone;
    Accidental accidental;

    Tone(char tone) {
        this.tone = tone;
        this.accidental = Accidental.Natural;
    }

    Tone(String tone) {
        this.tone = tone.charAt(0);

        if (tone.length() > 1) {
            this.accidental = Accidental.valueOf(Character.toString(tone.charAt(1)));
        }
    }
}