package MusicAPI.structure;

import java.io.Serializable;

class TimeSignature implements Serializable {
    byte pulses; //top, how many beats
    byte unit; //bottom, who gets the beat?

    public TimeSignature() {
        pulses = 4;
        unit = 4;
    }
}