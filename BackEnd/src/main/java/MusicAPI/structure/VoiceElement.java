package MusicAPI.structure;

import MusicAPI.harmonicsKB.rhythm.BeatDuration;

import javax.sound.midi.*;

/**
 * Created by ben on 2/7/2016.
 */
public abstract class VoiceElement {
    BeatDuration duration;

    public abstract int addToMidiTrack(Track midiTrack, int startingPosition);
}
