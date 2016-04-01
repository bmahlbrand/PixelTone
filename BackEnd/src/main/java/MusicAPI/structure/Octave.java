package MusicAPI.structure;

import java.io.Serializable;

public class Octave implements Serializable {
    // TODO THERE'S GOT TO BE A BETTAH WAY
    private int octave;

    public Octave(int octave) {
        if (octave > 10)
            throw new IllegalArgumentException();

        this.octave = octave;
    }

    public void stepUp() throws Exception {
        if (octave > 9)
            throw new Exception();
        octave++;
    }

    public void stepDown() throws Exception {
        if (octave < 1)
            throw new Exception();
        octave--;
    }

    int getOctaveMidi() {
        return octave * 12;
    }
}