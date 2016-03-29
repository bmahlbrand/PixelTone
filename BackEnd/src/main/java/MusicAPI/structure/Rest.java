package MusicAPI.structure;

import MusicAPI.harmonicsKB.rhythm.BeatDuration;

import java.io.Serializable;
import javax.sound.midi.*;

public class Rest extends VoiceElement implements Serializable {

	public Rest(BeatDuration duration) {
		this.duration = duration;
	}

    @Override
    public int getDuration() {
        return duration.getNumberOfSixtyFourthNotes() * 6;
    }

	public int addToMidiTrack(Track midiTrack, int startingPosition){
		return startingPosition + getDuration();
	}
}