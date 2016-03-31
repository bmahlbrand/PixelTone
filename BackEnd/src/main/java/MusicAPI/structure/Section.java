package MusicAPI.structure;

import MusicAPI.harmonicsKB.phrasing.SectionType;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;

public class Section implements Serializable {
    SectionType sectionType;
    Collection<Measure> measures;
    KeySignature key;
    TimeSignature timeSig;

    public Section(){
    	measures = new ArrayList<Measure> ();
    }

    public Collection<Measure> getMeasures(){
    	return measures;
    }

    public void addMeasure(Measure measure){
    	measures.add(measure);
    }
}