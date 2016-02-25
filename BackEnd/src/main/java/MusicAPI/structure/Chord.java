package MusicAPI.structure;

import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.harmonicsKB.intervals.Triad;

/**
 * Created by ben on 2/7/2016.
 */
public class Chord extends VoiceElement {
    Note root;
    Triad triad;
    Mode mode;
}
