package MusicAPI.structure;

import java.io.Serializable;

class TimeSignature implements Serializable {
    int pulses; //top, how many beats
    int unit; //bottom, who gets the beat?

    public TimeSignature() {
        pulses = 4;
        unit = 4;
    }

    public TimeSignature(int pulses, int unit) {
        if ((unit % 2) != 0) {
            throw new IllegalArgumentException("bad unit for time signature, must be multiple of 2!");
        }

        this.pulses = pulses;
        this.unit = unit;
    }
}