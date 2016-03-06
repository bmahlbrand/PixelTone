package MusicAPI.structure;

import java.io.Serializable;

public class Octave implements Serializable {

    private int octave;

    Octave(int octave) {
        if (octave > 10)
            throw new IllegalArgumentException();

        this.octave = octave;
    }

    int getOctaveMidi() {
        return octave * 12;
    }
}