package MusicAPI.virtuouso.models.genetic.fitness;

import MusicAPI.virtuouso.models.genetic.GeneticAlgorithm;
import MusicAPI.virtuouso.models.genetic.fitness.FitnessFunction;
import org.jenetics.Genotype;
import org.jenetics.IntegerGene;
import MusicAPI.virtuouso.weights.DegreeWeights;

public class CommonChordProgFitFunc implements FitnessFunction {

    public CommonChordProgFitFunc() {

    }

    @Override
    public Integer fitnessFunction(final Genotype<IntegerGene> gt) {

        final int[] chordProg = GeneticAlgorithm.genotypeToIntArray(gt);
        int score = 0;

        for (int count = 0; count < chordProg.length - 1; count++) {
            if (DegreeWeights.getCommon(chordProg[count] - 1, chordProg[count + 1] -1) == 1)
                score++;
        }

        return score;
    }

}