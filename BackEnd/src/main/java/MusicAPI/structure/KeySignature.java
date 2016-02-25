package MusicAPI.structure;

import MusicAPI.harmonicsKB.intervals.Mode;

import java.io.Serializable;

class KeySignature implements Serializable {
    Mode mode;
    Tone fifth;
    //alterations

    public KeySignature relativeKey(Mode relativeMode) {
        return null;
    }
}