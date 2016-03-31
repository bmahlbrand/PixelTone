package MusicAPI.structure;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;

public class Voice implements Serializable {
    Collection<Section> sections;
    Clef clef;

	public Voice(){
        sections = new ArrayList<Section> ();
        clef = Clef.Treble;
    }

    public Voice(Clef clef) {
        sections = new ArrayList<Section> ();
        this.clef = clef;
    }

    public Collection<Section> getSections(){
    	return sections;
    }

    public void addSection(Section section){
    	sections.add(section);
    }
}