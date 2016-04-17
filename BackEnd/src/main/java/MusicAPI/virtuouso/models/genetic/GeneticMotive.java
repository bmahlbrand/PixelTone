package MusicAPI.virtuouso.models.genetic;

import MusicAPI.virtuouso.models.genetic.fitness.GeneticMotiveFitnessFunction;
import MusicAPI.virtuouso.models.genetic.GeneticAlgorithm;
import org.jenetics.Genotype;
import org.jenetics.Phenotype;
import org.jenetics.IntegerGene;
import org.jenetics.IntegerGene;
import MusicAPI.structure.*;
import MusicAPI.harmonicsKB.scale.*;
import MusicAPI.harmonicsKB.triads.Triad;
import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.harmonicsKB.intervals.Degree;
import MusicAPI.harmonicsKB.rhythm.*;

public class GeneticMotive{

	Measure motiveContent;
	Note key;
	Mode mode;

	public GeneticMotive(){
		key = new Note("C");
		mode = Mode.Ionian;
		generateGeneticMotive(key, mode, new Measure());
	}

	public GeneticMotive(Note key, Mode mode, Measure harmonicPattern){
		this.key = key;
		this.mode = mode;

		generateGeneticMotive(key, mode, harmonicPattern);
	}

	public Measure getMotiveContent(){
		return motiveContent;
	}

	private void generateGeneticMotive(Note key, Mode mode, Measure harmonicPattern){
		GeneticMotiveFitnessFunction fitnessFunction = new GeneticMotiveFitnessFunction(key, mode, harmonicPattern);

		motiveContent = phenotypeToMeasure(GeneticAlgorithm.geneticAlgorithm(1, 8, 48, fitnessFunction));

	}

	private Measure phenotypeToMeasure(Phenotype<IntegerGene,Integer> phenotypeMeasure){
		final int[] measureContent = GeneticAlgorithm.phenotypeToIntArray(phenotypeMeasure);
		int numNotes = GeneticMotiveFitnessFunction.getQuantityOfNotesInMeasure(measureContent);
		Measure measure = new Measure();
		for (int i=0; i<numNotes; i++){
			Beat b1 = new Beat();
			b1.addNote(makeNote(measureContent[i*3], measureContent[i*3 + 1], measureContent[i*3 + 2]));
			measure.addBeat(b1);
		}
		return measure;

	}

	private Note makeNote (int rhythm, int step, int octave){
		DiatonicScale keySignature = new DiatonicScale(key, mode);
		Note currentNote = keySignature.getNote(Degree.numToDegree(step-1));
		currentNote = new Note(currentNote.getTone().toString());
		BeatDuration noteDuration = getNoteDuration(rhythm);
		currentNote.setDuration(noteDuration);
		currentNote.setOctave(new Octave(((octave-1)/4) + 3));
		return currentNote;
	}

	private BeatDuration getNoteDuration(int rhythm){
		switch(rhythm){
			case 1: return BeatDuration.Sixteenth;
			case 2: return BeatDuration.Eighth;
			case 3: return BeatDuration.DottedEighth;
			case 4: return BeatDuration.Quarter;
			case 5: return BeatDuration.DottedQuarter;
			case 6: return BeatDuration.Half;
			case 7: return BeatDuration.DottedHalf;
			case 8: return BeatDuration.Whole;
		}
		return BeatDuration.Quarter;
	}


}