package MusicAPI.structure;

import java.io.Serializable;
import java.util.Collection;

class Section implements Serializable {
    Collection<Measure> measures;
    KeySignature key;
    TimeSignature timeSig;
}