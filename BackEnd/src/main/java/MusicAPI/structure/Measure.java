package MusicAPI.structure;

import java.io.Serializable;
import java.sql.Time;
import java.util.Collection;
import java.util.ArrayList;

public class Measure implements Serializable {
    Collection<Beat> beats;
    int beatCapacity;

    public Measure(){
    	beats = new ArrayList<Beat> ();
    }

    public Measure(TimeSignature timeSignature){
        beats = new ArrayList<Beat>();
        beatCapacity = timeSignature.pulses;
    }

    public Collection<Beat> getBeats(){
    	return beats;
    }

    public void addBeat(Beat beat){
    	beats.add(beat);
    }
}