package MusicAPI.structure;

import MusicAPI.harmonicsKB.triads.Triad;
import MusicAPI.harmonicsKB.rhythm.BeatDuration;
import MusicAPI.virtuouso.MIDIGenerator;

import javax.sound.midi.*;

/**
 * Created by ben on 2/7/2016.
 */
public class Chord extends VoiceElement {
    Triad triad;

    public Chord(Triad triad, BeatDuration duration) {
        this.triad = triad;
        this.duration = duration;
    }

    @Override
    public int getDuration() {
        return duration.getNumberOfSixtyFourthNotes() * 6;
    }

    public  int addToMidiTrack(Track midiTrack, int startingPosition) {

        for (Note note : triad.getNotes()) {
            int midiNoteFrequency = MIDIGenerator.getNoteFrequency(note.tone.index(), note.octave.getOctaveMidi());
            try {

                ShortMessage currentNote = new ShortMessage(ShortMessage.NOTE_ON, 0, midiNoteFrequency, note.dynamics.getVolume() - 30);
                midiTrack.add(new MidiEvent(currentNote, startingPosition));

            } catch (Exception e) {}
        }

        startingPosition += getDuration();

        for (Note note : triad.getNotes()) {
            int midiNoteFrequency = MIDIGenerator.getNoteFrequency(note.tone.index(), note.octave.getOctaveMidi());
            try {

                ShortMessage currentNote = new ShortMessage(ShortMessage.NOTE_OFF, 0, midiNoteFrequency, note.dynamics.getVolume());
                midiTrack.add(new MidiEvent(currentNote, startingPosition));

            } catch (Exception e) {}
        }
        return startingPosition;
    }

    public Triad getTriad(){
        return triad;
    }

}
