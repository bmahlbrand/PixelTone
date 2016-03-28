package MusicAPI.structure;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;

public class Voice implements Serializable {
    Collection<Section> sections;

	public Voice(){
    	sections = new ArrayList<Section> ();
    }
    public Collection<Section> getSections(){
    	return sections;
    }

    public void addSection(Section section){
    	sections.add(section);
    }
}