package MusicAPI.virtuouso.models.genetic;

import MusicAPI.structure.*;
import MusicAPI.harmonicsKB.scale.*;
import MusicAPI.harmonicsKB.triads.*;
import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.harmonicsKB.intervals.Degree;
import MusicAPI.harmonicsKB.rhythm.*;
import MusicAPI.harmonicsKB.phrasing.Cadence;
import MusicAPI.virtuouso.MIDIGenerator;

public class AccompanimentRhythm{

	public static Measure albertiBass(Measure sustainedChords){
		Measure newMeasure = new Measure();
		for(Beat currentBeat: sustainedChords.getBeats()){
			for (VoiceElement currentChord: currentBeat.getVoiceElements()){
				addAlbertiBass(newMeasure, (Chord)currentChord);
			}
		}
		return newMeasure;
	}

	public static Measure arpegiattedBass(Measure sustainedChords){
		Measure newMeasure = new Measure();
		for(Beat currentBeat: sustainedChords.getBeats()){
			for (VoiceElement currentChord: currentBeat.getVoiceElements()){
				addArpegiattedBass(newMeasure, (Chord)currentChord);
			}
		}
		return newMeasure;
	}

	public static Measure eighthNoteBass(Measure sustainedChords){
		Measure newMeasure = new Measure();
		for(Beat currentBeat: sustainedChords.getBeats()){
			for (VoiceElement currentChord: currentBeat.getVoiceElements()){
				Beat newBeat = new Beat();
				newBeat.addChord(new Chord(((Chord)currentChord).getTriad(), BeatDuration.Eighth));
				for (int i=0; i<4; i++)
					newMeasure.addBeat(newBeat);
			}
		}
		return newMeasure;

	}

	public static Measure quarterNoteBass(Measure sustainedChords){
		Measure newMeasure = new Measure();
		for(Beat currentBeat: sustainedChords.getBeats()){
			for (VoiceElement currentChord: currentBeat.getVoiceElements()){
				Beat newBeat = new Beat();
				newBeat.addChord(new Chord(((Chord)currentChord).getTriad(), BeatDuration.Quarter));
				for (int i=0; i<2; i++)
					newMeasure.addBeat(newBeat);
			}
		}
		return newMeasure;
	}

	public static Measure halfNoteRoot(Measure sustainedChords){
		Measure newMeasure = new Measure();
		for(Beat currentBeat: sustainedChords.getBeats()){
			for (VoiceElement currentChord: currentBeat.getVoiceElements()){
				addHalfNoteBass(newMeasure, (Chord) currentChord);
			}
		}
		return newMeasure;
	}

	private static void addHalfNoteBass(Measure currentMeasure, Chord currentChord){
		Triad currentTriad = currentChord.getTriad();
		Note[] triad = new Note[3];
		triad = currentTriad.getNotes().toArray(triad);

		int[] noteOctaves = getNoteOctaves(triad);

		Beat newBeat = new Beat();
		Note currentNote = new Note(triad[0].getTone().toString(), BeatDuration.Half);
		currentNote.setOctave(new Octave(noteOctaves[0]  - 1));
		newBeat.addNote(currentNote);
		currentMeasure.addBeat(newBeat);

	}

	private static void addAlbertiBass(Measure currentMeasure, Chord currentChord){
		Triad currentTriad = currentChord.getTriad();
		Note[] triad = new Note[3];
		triad = currentTriad.getNotes().toArray(triad);

		int[] noteOctaves = getNoteOctaves(triad);

		Beat newBeat = new Beat();
		Note currentNote = new Note(triad[0].getTone().toString(), BeatDuration.Eighth);
		currentNote.setOctave(new Octave(noteOctaves[0]));
		newBeat.addNote(currentNote);
		currentMeasure.addBeat(newBeat);

		newBeat = new Beat();
		currentNote = new Note(triad[2].getTone().toString(), BeatDuration.Eighth);
		currentNote.setOctave(new Octave(noteOctaves[2]));
		newBeat.addNote(currentNote);
		currentMeasure.addBeat(newBeat);

		newBeat = new Beat();
		currentNote = new Note(triad[1].getTone().toString(), BeatDuration.Eighth);
		currentNote.setOctave(new Octave(noteOctaves[1]));
		newBeat.addNote(currentNote);
		currentMeasure.addBeat(newBeat);

		newBeat = new Beat();
		currentNote = new Note(triad[2].getTone().toString(), BeatDuration.Eighth);
		currentNote.setOctave(new Octave(noteOctaves[2]));
		newBeat.addNote(currentNote);
		currentMeasure.addBeat(newBeat);
	}

	private static void addArpegiattedBass(Measure currentMeasure, Chord currentChord){
		Triad currentTriad = currentChord.getTriad();
		Note[] triad = new Note[3];
		triad = currentTriad.getNotes().toArray(triad);

		int[] noteOctaves = getNoteOctaves(triad);

		Beat newBeat = new Beat();
		Note currentNote = new Note(triad[0].getTone().toString(), BeatDuration.Eighth);
		currentNote.setOctave(new Octave(noteOctaves[0]));
		newBeat.addNote(currentNote);
		currentMeasure.addBeat(newBeat);

		newBeat = new Beat();
		currentNote = new Note(triad[1].getTone().toString(), BeatDuration.Eighth);
		currentNote.setOctave(new Octave(noteOctaves[1]));
		newBeat.addNote(currentNote);
		currentMeasure.addBeat(newBeat);

		newBeat = new Beat();
		currentNote = new Note(triad[2].getTone().toString(), BeatDuration.Eighth);
		currentNote.setOctave(new Octave(noteOctaves[2]));
		newBeat.addNote(currentNote);
		currentMeasure.addBeat(newBeat);

		newBeat = new Beat();
		currentNote = new Note(triad[1].getTone().toString(), BeatDuration.Eighth);
		currentNote.setOctave(new Octave(noteOctaves[1]));
		newBeat.addNote(currentNote);
		currentMeasure.addBeat(newBeat);
	}

	private static int[] getNoteOctaves(Note[] notes){
		int rootFrequency = MIDIGenerator.getNoteFrequency(notes[0].getTone().index(), 3);
		int thirdFrequency = MIDIGenerator.getNoteFrequency(notes[1].getTone().index(), 3);
		int fifthFrequency = MIDIGenerator.getNoteFrequency(notes[2].getTone().index(), 3);

		int[] noteOctaves = {2, 2, 2};
		if (thirdFrequency < rootFrequency){
			noteOctaves[1] = 3;
		}

		if (fifthFrequency < thirdFrequency || fifthFrequency < rootFrequency){
			noteOctaves[2] = 3;
		}

		return noteOctaves;

	}
}