package MusicAPI.virtuouso.models.genetic;

import MusicAPI.structure.*;
import MusicAPI.harmonicsKB.scale.*;
import MusicAPI.harmonicsKB.triads.Triad;
import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.harmonicsKB.intervals.Degree;
import MusicAPI.harmonicsKB.rhythm.*;
import MusicAPI.harmonicsKB.phrasing.Cadence;
import MusicAPI.virtuouso.models.genetic.GeneticMotive;
import MusicAPI.virtuouso.models.genetic.GeneticHarmonicProgression;

public class GeneticSimpleComposition{
	Note key;
	Mode mode;
	int tempo;
	Composition generatedSong;

	public GeneticSimpleComposition(){
		key = new Note("C");
		mode = Mode.Ionian;
		tempo = 90;

		composeSong();
	}

	public GeneticSimpleComposition(Note key, Mode mode, int tempo){
		this.key = key;
		this.mode = mode;
		this.tempo = tempo;

		composeSong();
	}

	private void composeSong(){
		generatedSong = new Composition(tempo);

		Section aSectionChordProgression = null;
		Section aSectionMelody = null;

		Voice chordProgressions = new Voice();
		Section currentChordProgression;
		Voice melody = new Voice();
		Section currentMelody;
		for (int i=0; i<2; i++){
			currentChordProgression = generateChordProgression(i);
			currentMelody = generateMelody(currentChordProgression);

			if (i==0){
				aSectionMelody = currentMelody;
				aSectionChordProgression = currentChordProgression;
			}

			chordProgressions.addSection(currentChordProgression);
			chordProgressions.addSection(currentChordProgression);
			melody.addSection(currentMelody);
			melody.addSection(currentMelody);
		}

		chordProgressions.addSection(aSectionChordProgression);
		chordProgressions.addSection(aSectionChordProgression);
		melody.addSection(aSectionMelody);
		melody.addSection(aSectionMelody);

		generatedSong.addVoice(chordProgressions);
		generatedSong.addVoice(melody);
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
			currentMotive = new GeneticMotive(key, mode, currentMeasure);
			melody.addMeasure(currentMotive.getMotiveContent());
		}

		return melody;
	}
}