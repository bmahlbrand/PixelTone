package MusicAPI.structure;

import MusicAPI.harmonicsKB.dynamics.Accent;
import MusicAPI.harmonicsKB.dynamics.Dynamics;
import MusicAPI.harmonicsKB.rhythm.BeatDuration;

import MusicAPI.virtuouso.MIDIGenerator;

import javax.sound.midi.*;

import java.io.Serializable;

public class Note extends VoiceElement implements Serializable {
    Tone tone;
    Octave octave;
    Dynamics dynamics;
    Accent accent;

    //assumes tone, accidental, optional octave [A-G][#|b][0-8]
    public Note(String note) {
        this.tone = new Tone(note);
        this.dynamics = Dynamics.Forte;
        this.accent = Accent.Unaccented;
        this.octave = new Octave(3);
    }

    public Note(String note, BeatDuration rhythm) {
        this.tone = new Tone(note);
        this.dynamics = Dynamics.Forte;
        this.accent = Accent.Unaccented;
        this.octave = new Octave(3);
        duration = rhythm;
    }

    public Note(int index) {
        this.tone = Tone.fromIndex(index);
    }

    public Accidental getAccidental() {
        return tone.accidental;
    }

    public int getIndex() {
        return tone.index();
    }

    public Note getHalfStep() {
        return new Note(tone.halfStep().toString());
    }

    public Note getHalfStep(BeatDuration duration) {
        return new Note(tone.halfStep().toString(), duration);
    }

    public Note getWholeStep() {
        return new Note(tone.wholeStep().toString());
    }

    public Note getWholeStep(BeatDuration duration) {
        return new Note(tone.wholeStep().toString(), duration);
    }

    public Octave getOctave() {
        return octave;
    }

    @Override
    public String toString() {
        return tone.toString() + duration.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!Note.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final Note other = (Note) obj;

        if (this.tone == other.tone
                && this.duration == other.duration
                && this.octave == other.octave)
            return true;
        return false;
    }

    public static void main() {
        System.out.println(new Note("A"));
    }

    public int addToMidiTrack(Track midiTrack, int startingPosition){
        int midiNoteFrequency = MIDIGenerator.getNoteFrequency(tone.index(), octave.getOctaveMidi());
        try {
            ShortMessage currentNote = new ShortMessage(ShortMessage.NOTE_ON, 0, midiNoteFrequency, dynamics.getVolume());
            midiTrack.add(new MidiEvent(currentNote, startingPosition));

            startingPosition += 6 * duration.getNumberOfSixtyFourthNotes();

            currentNote = new ShortMessage(ShortMessage.NOTE_OFF, 0, midiNoteFrequency, dynamics.getVolume());
            midiTrack.add(new MidiEvent(currentNote, startingPosition));
        }
        catch(Exception e){}
        
        return startingPosition;
    }
}