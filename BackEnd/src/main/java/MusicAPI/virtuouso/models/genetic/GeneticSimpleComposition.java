package MusicAPI.virtuouso.models.genetic;

import java.util.Random;

import MusicAPI.structure.*;
import MusicAPI.harmonicsKB.scale.*;
import MusicAPI.harmonicsKB.triads.Triad;
import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.harmonicsKB.intervals.Degree;
import MusicAPI.harmonicsKB.rhythm.*;
import MusicAPI.harmonicsKB.phrasing.Cadence;
import MusicAPI.virtuouso.models.genetic.GeneticMotive;
import MusicAPI.virtuouso.models.genetic.AccompanimentRhythm;
import MusicAPI.virtuouso.models.genetic.GeneticHarmonicProgression;

public class GeneticSimpleComposition{
	Note key;
	Mode mode;
	int tempo;
	int chaos;
	int numVoices;
	Composition generatedSong;

	final int SAD_TEMPO = 70;
	final int NEUTRAL_TEMPO = 100;
	final int HAPPY_TEMPO = 120;

	public GeneticSimpleComposition(){
		key = new Note("C");
		mode = Mode.Ionian;
		tempo = 90;

		composeSong();
	}

	public GeneticSimpleComposition(Note key, Mode mode, int tempo, int chaos, int numVoices){
		this.key = key;
		this.mode = mode;
		this.tempo = tempo;
		this.chaos = chaos;
		this.numVoices = numVoices;

		composeSong();
	}

	private void composeSong(){
		generatedSong = new Composition(tempo);

		Section aSectionChordProgression = null;
		Section aSectionMelody = null;
		Section aChordRoots = null;

		Voice chordProgressions = new Voice();
		Section currentChordProgression;

		Voice chordRoots = new Voice();
		Section currentChordRoots;

		Voice melody = new Voice();
		Section currentMelody;
		for (int i=0; i<2; i++){
			currentChordProgression = generateChordProgression(i);
			currentMelody = generateMelody(currentChordProgression);
			currentChordRoots = generateChordRoots(currentChordProgression);

			currentChordProgression = setChordProgressionRhythm(currentChordProgression);

			if (i==0){
				aSectionMelody = currentMelody;
				aSectionChordProgression = currentChordProgression;
				aChordRoots  = currentChordRoots;
			}

			melody.addSection(currentMelody);
			melody.addSection(currentMelody);

			if (numVoices >=2){
				chordProgressions.addSection(currentChordProgression);
				chordProgressions.addSection(currentChordProgression);
			}

			if (numVoices>= 3){
				chordRoots.addSection(currentChordRoots);
				chordRoots.addSection(currentChordRoots);

			}
			
		}

		melody.addSection(aSectionMelody);
		melody.addSection(aSectionMelody);

		if (numVoices >=2){
			chordProgressions.addSection(aSectionChordProgression);
			chordProgressions.addSection(aSectionChordProgression);
		}

		if (numVoices>= 3){
				chordRoots.addSection(aChordRoots);
				chordRoots.addSection(aChordRoots);

			}


		generatedSong.addVoice(chordProgressions);
		generatedSong.addVoice(melody);
		generatedSong.addVoice(chordRoots);
	}

	public Composition getGeneratedSong(){
		return generatedSong;
	}

	private Section generateChordProgression(int phraseNumber){
		GeneticHarmonicProgression currentProgression;
		if (phraseNumber == 0)
			currentProgression = new GeneticHarmonicProgression(key, mode,Cadence.Authentic);
		else
			currentProgression = new GeneticHarmonicProgression(key, mode,Cadence.Half);

		return currentProgression.getHarmonicProgression();
	}

	private Section generateMelody(Section chordProgression){
		Section melody = new Section();
		GeneticMotive currentMotive;
		for (Measure currentMeasure: chordProgression.getMeasures()){
			currentMotive = new GeneticMotive(key, mode, currentMeasure, chaos);
			melody.addMeasure(currentMotive.getMotiveContent());
		}

		return melody;
	}

	private Section generateChordRoots(Section chordProgression){
		Section roots = new Section();
		for (Measure currentMeasure: chordProgression.getMeasures()){
			roots.addMeasure(AccompanimentRhythm.halfNoteRoot(currentMeasure));
		}

		return roots;
	}

	private Section makeAccompanimentArpeggiated(Section chordProgression){
		Section newSection = new Section();
		for (Measure currentMeasure: chordProgression.getMeasures()){
			newSection.addMeasure(AccompanimentRhythm.arpegiattedBass(currentMeasure));
		}
		return newSection;
	}

	private Section makeAccompanimentAlbertiBass(Section chordProgression){
		Section newSection = new Section();
		for (Measure currentMeasure: chordProgression.getMeasures()){
			newSection.addMeasure(AccompanimentRhythm.albertiBass(currentMeasure));
		}
		return newSection;
	}

	private Section makeAccompanimentQuarterNotes(Section chordProgression){
		Section newSection = new Section();
		for (Measure currentMeasure: chordProgression.getMeasures()){
			newSection.addMeasure(AccompanimentRhythm.quarterNoteBass(currentMeasure));
		}
		return newSection;
	}

	private Section makeAccompanimentEighthNotes(Section chordProgression){
		Section newSection = new Section();
		for (Measure currentMeasure: chordProgression.getMeasures()){
			newSection.addMeasure(AccompanimentRhythm.eighthNoteBass(currentMeasure));
		}
		return newSection;
	}

	private Section setChordProgressionRhythm(Section chordProgression){
		Random rng = new Random();
		boolean randomBoolean = rng.nextBoolean();
		if (tempo <= SAD_TEMPO){
			if (randomBoolean)
				return makeAccompanimentArpeggiated(chordProgression);
			return makeAccompanimentAlbertiBass(chordProgression);
		}

		if (tempo <= NEUTRAL_TEMPO){
			if (randomBoolean)
				return makeAccompanimentQuarterNotes(chordProgression);
			return chordProgression;

		}
		if (tempo <= HAPPY_TEMPO){
			if (randomBoolean)
				return makeAccompanimentQuarterNotes(chordProgression);
			return makeAccompanimentAlbertiBass(chordProgression);
		}
		if (randomBoolean)
			return makeAccompanimentEighthNotes(chordProgression);
		return makeAccompanimentQuarterNotes(chordProgression);
	}
}