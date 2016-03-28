package MusicAPI.structure;

import MusicAPI.harmonicsKB.rhythm.BeatDuration;

import java.util.ArrayList;
import javax.sound.midi.*;

/**
 * Created by ben on 3/5/2016.
 */
public class Tuplet extends VoiceElement {

    /*
    * typical = the total space you want the tuplet to occupy, ie a triplet of eighth notes in a quarter note
    * actual is 3 eighth notes in this case
    */

    ArrayList<BeatDuration> actual;
    ArrayList<BeatDuration> typical;

    public Tuplet() {


    }
    public  int addToMidiTrack(Track midiTrack, int startingPosition){ return startingPosition;}
}

