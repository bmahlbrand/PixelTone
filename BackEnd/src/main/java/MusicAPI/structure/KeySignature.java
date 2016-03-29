package MusicAPI.structure;

import MusicAPI.harmonicsKB.intervals.Mode;

import java.io.Serializable;

class KeySignature implements Serializable {
    Mode mode;
    Tone fifth;
    //alterations

    public KeySignature relativeKey(Mode relativeMode) {
        return null; //TODO implement
    }

    public Chord borrowedChord() {
        Chord ret = null;

        switch(mode) {//TODO implement
            case Ionian:
                break;
            case Aeolian:
                break;
        }

        return ret;
    }
}