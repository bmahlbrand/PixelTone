package MusicAPI.structure;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;

public class Measure implements Serializable {
    Collection<Beat> beats;

    public Measure(){
    	beats = new ArrayList<Beat> ();
    }

    public Collection<Beat> getBeats(){
    	return beats;
    }

    public void addBeat(Beat beat){
    	beats.add(beat);
    }
}