package MusicAPI.virtuouso.models.genetic.fitness;

import MusicAPI.virtuouso.models.genetic.GeneticAlgorithm;
import MusicAPI.virtuouso.models.genetic.fitness.FitnessFunction;
import org.jenetics.Genotype;
import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;
import MusicAPI.structure.*;
import MusicAPI.harmonicsKB.scale.*;
import MusicAPI.harmonicsKB.triads.Triad;
import MusicAPI.harmonicsKB.intervals.Mode;
import MusicAPI.harmonicsKB.intervals.Degree;
import MusicAPI.harmonicsKB.rhythm.*;
import java.util.*;

public class GeneticMotiveFitnessFunction implements FitnessFunction{
	
	Measure harmonicPattern;
	Note key;
	Mode mode;


	public GeneticMotiveFitnessFunction(Note key, Mode mode, Measure harmonicPattern){
		this.harmonicPattern = harmonicPattern;
		this.key = key;
		this.mode = mode;
	}

	@Override
	public Integer fitnessFunction(final Genotype<IntegerGene> gt){
		final int[] noteList = GeneticAlgorithm.genotypeToIntArray(gt);
        int score = 0;
        int totalNotes = 0;
        int currentRhytmicPosition = 0;

        score+= checkForMeasureLength(noteList);
        for (int i = 0; i<16; i++){
        	score+= scoreNote(noteList, i, currentRhytmicPosition);
        	totalNotes += addToTotalNotes(currentRhytmicPosition);
        	currentRhytmicPosition += rhytmicLengthOfNote(noteList[i*3]);

        }

        score+= checkForALeap(noteList, totalNotes);
        score+= checkForRhytmicMonotony(noteList, totalNotes);
        score+= checkForTooBusyRhythm(totalNotes);

        return score;
	}

	private int addToTotalNotes(int currentRhytmicPosition){
		if(currentRhytmicPosition< 16)
			return 1;
		return 0;
	}

	private int scoreNote(int[] noteList, int position, int currentRhytmicPosition){
		if(currentRhytmicPosition>=16)
			return 0;

		int score = 0;

		if(currentRhytmicPosition == 4 || currentRhytmicPosition == 12 ){
			score += isChordTone(noteList, position, currentRhytmicPosition);
		}
		if(currentRhytmicPosition == 0 || currentRhytmicPosition == 8 ){
			score += 2*isChordTone( noteList,  position,  currentRhytmicPosition);
		}
		if(currentRhytmicPosition!= 0){
			score += isPassingNoteOrNeighborTone( noteList,  position, currentRhytmicPosition);
		}
		return score;
	}

	private static int rhytmicLengthOfNote(int rhythm){
		switch(rhythm){
			case 1: return 1;
			case 2: return 2;
			case 3: return 3;
			case 4: return 4;
			case 5: return 6;
			case 6: return 8;
			case 7: return 12;
			case 8: return 16;
		}
		return 0;
	}

	private int checkForMeasureLength(int[] noteList){
		boolean measureFilled = false;
		int currentRhytmicPosition = 0;
		for (int i=0; i<16; i++){
			currentRhytmicPosition += rhytmicLengthOfNote(noteList[i*3]);
			if (currentRhytmicPosition == 16)
				measureFilled = true;
		}
		if (measureFilled)
			return 0;
		return -1000;
	}

	private int checkForRhytmicMonotony(int[] noteList, int totalNotes){
		boolean rhythmChanges = false;

		for(int i=0; i<totalNotes-1; i++){
			if (noteList[i*3] != noteList[(i+1)*3])
				rhythmChanges = true;
		}

		if (rhythmChanges)
			return 0;
		return -100;
	}

	private int checkForALeap(int[] noteList, int totalNotes){
		boolean leapExists = false;

		for(int i = 0; i< totalNotes-1; i++){
			if (!adjacentNotes(noteList[i*3 + 1], noteList[i*3 + 2], noteList[(i+1)*3 + 1], noteList[(i+1)*3 + 2]))
				leapExists = true;
		}

		if (leapExists)
			return 20;
		return 0;
	}

	private int checkForTooBusyRhythm(int totalNotes){
		double averageRhythm = 16.0/totalNotes;
		if(averageRhythm<2)
			return -50;
		return 0;
	}

	private int isChordTone(int[] noteList, int position, int currentRhytmicPosition){
		Collection<Beat> beats = harmonicPattern.getBeats();
		int chordRhythmicPosition = 0;
		for(Beat beat: beats){
			Collection<VoiceElement> chords = beat.getVoiceElements();
			for(VoiceElement chord: chords){
				if (chordRhythmicPosition <= currentRhytmicPosition && chordRhythmicPosition + chord.getDuration()/4 >= currentRhytmicPosition){
					DiatonicScale keySignature = new DiatonicScale(key, mode);
					Note currentNote = keySignature.getNote(Degree.numToDegree(noteList[position*3 + 1] - 1));
					Triad chordTriad = ((Chord)chord).getTriad();
					if(chordTriad.noteInTriad(currentNote)){
						return 50;
					}
						

				}
				chordRhythmicPosition+= chord.getDuration()/4;
			}
		}

		return 0;

	}

	private int isPassingNoteOrNeighborTone(int[] noteList, int position, int currentRhytmicPosition){
		if (adjacentNotes(noteList[position*3 + 1], noteList[position*3 + 2], noteList[(position-1)*3 + 1], noteList[(position-1)*3 + 2])){
			if (adjacentNotes(noteList[position*3 + 1], noteList[position*3 + 2], noteList[(position+1)*3 + 1], noteList[(position+1)*3 + 2]))
				return 15;
		}	
		return 0;
	}

	private boolean adjacentNotes (int keyScaleOne, int octaveOne, int keyScaleTwo, int octaveTwo){
		if (keyScaleOne == 8 && octaveOne == 1){
			keyScaleOne = 1;
			octaveOne = 2;
		}

		if (keyScaleTwo == 8 && octaveTwo == 1){
			keyScaleTwo = 1;
			octaveTwo = 2;
		}

		if ((keyScaleOne == keyScaleTwo+1 || keyScaleOne == keyScaleTwo -1) && octaveOne == octaveTwo)
			return true;
		if ((keyScaleOne == 7 && octaveOne == 1 && keyScaleTwo== 1 && octaveTwo == 2) || (keyScaleOne == 1 && octaveOne == 2 && keyScaleTwo== 7 && octaveTwo == 1))
			return true;
		return false;
	}

	public static int  getQuantityOfNotesInMeasure(int[] noteList){
		int totalLength = 0;
		int totalNotesInMeasure = 0;
		for (int i=0; i< 16; i++){
			if (totalLength < 16){
				totalNotesInMeasure++;
				totalLength+= rhytmicLengthOfNote(noteList[i*3]);
			}
		}
		return totalNotesInMeasure;
	}
}

/***********************************
Genetic Algorithm:

Return a set of numbers 1-8 (48 in total). 
Each three numbers represents a single note.
The first number represents rhythm.
1 = 16th note
2 = 8th note
3 = dotted 8th note
4 = quarter note
5 = dotted quarter note
6 = half note
7 = 7 dotted half note
8 = 8 whole note

The second number represents key scale.
1-8 duh

The third number represents upper or lower octave
1-4 lower octave
1-5 higher octave

Score any generation as follows.
If the first n rhythms don't sum up to exactly a measure (16 16th notes). Set score to -1000

Having a chord tone start on a beat is +50
Having a chord tone start on a strong beat is +100
Proper usage of a passing note is +15
Proper usage of a neighbor tone is +15
Having a measure containing all of the same rhythmic type is -100
Having a measure where the average rhythmic length is less than an eight note -50
Usage of non-stepwise motion somewhere in the measure is worth +20
Chord tones used per chord (+40 for 3, + 20 for 2)
**************************************************/