package MusicAPI.structure;

import java.io.Serializable;
import javax.sound.midi.*;

class Rest extends VoiceElement implements Serializable {
	public  int addToMidiTrack(Track midiTrack, int startingPosition){ return startingPosition;}

}