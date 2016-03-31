package MusicAPI.virtuouso.models.genetic;


import MusicAPI.virtuouso.models.genetic.fitness.CommonChordProgFitFunc;
import MusicAPI.virtuouso.models.genetic.GeneticAlgorithm;
import org.jenetics.Genotype;
import org.jenetics.Phenotype;
import org.jenetics.IntegerGene;
import MusicAPI.structure.*;
import MusicAPI.harmonicsKB.scale.*;
import MusicAPI.harmonicsKB.triads.*;
import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.harmonicsKB.intervals.Degree;
import MusicAPI.harmonicsKB.rhythm.*;
import MusicAPI.harmonicsKB.phrasing.Cadence;

public class GeneticHarmonicProgression{

	Note key;
	Mode mode;
	Cadence cadence;
	Section harmonicProgression;

	public GeneticHarmonicProgression(){
		key = new Note("C");
		mode = Mode.Ionian;
		cadence = Cadence.Authentic;
		generateGeneticHarmonicProgression(key, mode, cadence);
	}

	public GeneticHarmonicProgression(Note key, Mode mode, Cadence cadence){
		this.key = key;
		this.mode = mode;
		this.cadence = cadence;

		generateGeneticHarmonicProgression(key, mode, cadence);
	}

	private void generateGeneticHarmonicProgression(Note key, Mode mode, Cadence cadence){
		CommonChordProgFitFunc fitnessFunction = new CommonChordProgFitFunc(cadence);

		harmonicProgression = phenotypeToSection(GeneticAlgorithm.geneticAlgorithm(1, 7, 8, fitnessFunction));
	}

	public Section getHarmonicProgression(){
		return harmonicProgression;
	}

	private Section phenotypeToSection(Phenotype<IntegerGene,Integer> phenotypeSection){
		final int[] sectionContent = GeneticAlgorithm.phenotypeToIntArray(phenotypeSection);

		Section thisSection = new Section();
		for(int i=0; i< 8; i++){
			thisSection.addMeasure(makeMeasureOfChord(sectionContent[i]));
		}
		return thisSection;
	}

	private Measure makeMeasureOfChord(int chord){
		Measure currentMeasure = new Measure();
		Beat currentBeat = new Beat();
		Triad currentTriad = makeTriadFromChord(chord);
		currentBeat.addChord (new Chord(currentTriad, BeatDuration.Whole));

		currentMeasure.addBeat(currentBeat);
		return currentMeasure;
	}

	private Triad makeTriadFromChord(int chord){
		if (mode == Mode.Ionian)
			return makeTriadFromChordMajorKey (chord);
		return makeTriadFromChordMinorKey(chord);
	}

	private Triad makeTriadFromChordMinorKey(int chord){
		switch(chord){
			case 3:
			case 6:
			case 5:
			return makeMajorTriadFromKeyScale(chord);
			case 4:
			return makeMajorTriadFromKeyScale(chord);
			case 2:
			case 7:
			return makeDiminishedTriadFromKeyScale(chord);
		}
		return makeMajorTriadFromKeyScale(chord);
	}

	private Triad makeTriadFromChordMajorKey(int chord){
		switch(chord){
			case 1:
			case 4:
			case 5:
			return makeMajorTriadFromKeyScale(chord);
			case 2:
			case 3:
			case 6:
			return makeMinorTriadFromKeyScale(chord);
		}
		return makeDiminishedTriadFromKeyScale(chord);
	}

	private Triad makeMajorTriadFromKeyScale(int degree){
		DiatonicScale currentKey = new DiatonicScale(key, mode);
		Note rootNote = currentKey.getNote(Degree.numToDegree(degree - 1));

		return new MajorTriad(rootNote);
	}

	private Triad makeMinorTriadFromKeyScale(int degree){
		DiatonicScale currentKey = new DiatonicScale(key, mode);
		Note rootNote = currentKey.getNote(Degree.numToDegree(degree - 1));

		return new MinorTriad(rootNote);

	}

	private Triad makeDiminishedTriadFromKeyScale(int degree){
		DiatonicScale currentKey = new DiatonicScale(key, mode);
		Note rootNote = currentKey.getNote(Degree.numToDegree(degree - 1));

		return new DiminishedTriad(rootNote);

	}
}