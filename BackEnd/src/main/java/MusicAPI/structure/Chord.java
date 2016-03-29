package MusicAPI.structure;

import MusicAPI.harmonicsKB.triads.Triad;
import MusicAPI.harmonicsKB.rhythm.BeatDuration;

/**
 * Created by ben on 2/7/2016.
 */
public class Chord extends VoiceElement {
    Note root;
    Triad triad;

    public Chord(Note root, Triad triad, BeatDuration duration) {

    }
}
