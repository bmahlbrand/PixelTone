package MusicAPI.structure;

import MusicAPI.harmonicsKB.phrasing.SectionType;

import java.io.Serializable;
import java.util.Collection;

class Section implements Serializable {
    SectionType sectionType;
    Collection<Measure> measures;
    KeySignature key;
    TimeSignature timeSig;

}