package MusicAPI.structure;

import KeySignature;
import TimeSignature;

import java.util.Collections;

class Section {
    Collections<Measures> measures;
    KeySignature key;
    TimeSignature timeSig;
}