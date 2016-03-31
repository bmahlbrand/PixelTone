package MusicAPI.virtuouso.models.genetic.fitness;

import MusicAPI.virtuouso.models.genetic.GeneticAlgorithm;
import MusicAPI.virtuouso.models.genetic.fitness.FitnessFunction;
import org.jenetics.Genotype;
import org.jenetics.IntegerGene;
import MusicAPI.virtuouso.weights.DegreeWeights;
import MusicAPI.harmonicsKB.phrasing.Cadence;

public class CommonChordProgFitFunc implements FitnessFunction {
    Cadence thisCadence;


    public CommonChordProgFitFunc(){
        thisCadence = Cadence.Authentic;
    }

    public CommonChordProgFitFunc(Cadence thisCadence){
        this.thisCadence = thisCadence;
    }

    @Override
    public Integer fitnessFunction(final Genotype<IntegerGene> gt){
        int score =0;

        final int[] chordList = GeneticAlgorithm.genotypeToIntArray(gt);

        for (int i=0; i<chordList.length; i++){
            score+=checkValidChord(i, chordList);
        }

        score+= scoreFinalChords(chordList);

        return score;

    }

    private int checkValidChord(int position, int[] chordList){
        score=0;
        if (position =0){
            if (chordList[position] != 1)
                score=-100;
        }
        else{
            score = 5 * (int)DegreeWeights.get(chordList[position - 1] - 1, chordList[position] - 1)
        }
        return score;
    }

    private int scoreFinalChords(int[] chordList){
        return scorePenultimteChord(chordList) + scoreFinalChord (chordList);
    }

    private int scorePenultimteChord(int[] chordList){
        switch(thisCadence){
            case Half: return 0;
            case Authentic: if (chordList[chordList.length-2] == 5)
                            return 50;
            case Plagal: if (chordList[chordList.length-2] == 4)
                            return 50;
            case Interrupted: if (chordList[chordList.length-2] == 5)
                            return 50;
        }
        return 0;

    }

    private int scoreFinalChord(int[] chordList){
        switch(thisCadence){
            case Half: if (chordList[chordList.length-1] == 5)
                            return 50;
            case Authentic: if (chordList[chordList.length-1] == 1)
                            return 50;
            case Plagal: if (chordList[chordList.length-1] == 1)
                            return 50;
            case Interrupted: if (chordList[chordList.length-1] == 6)
                            return 50;
        }
        return 0;

    }

}