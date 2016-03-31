package MusicAPI.structure;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;

public class Composition implements Serializable {
    MetaData metadata;
    int tempo;

    Collection<Voice> voices;

    public Composition(int tempo){
    	this.tempo = tempo;
      this.voices = new ArrayList<Voice>();
    }

    public Composition(){

    }

    public void addVoice(Voice voice) {
        voices.add(voice);
    }

    public Collection<Voice> getVoices(){
    	return voices;
    }

    public int getTempo(){
        return tempo;
    }

}
