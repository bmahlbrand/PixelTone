package MusicAPI.virtuouso.models.genetic;

import org.jenetics.Genotype;
import org.jenetics.IntegerGene;

public class CommonChordProgFitFunc implements FitnessFunction {

    public CommonChordProgFitFunc() {

    }

    @Override
    public Integer fitnessFunction(final Genotype<IntegerGene> gt) {

        final int[] chordProg = GeneticAlgorithm.genotypeToIntArray(gt);
        int score = 0;
        int[][] commonChordProg = {
                {1, 1, 1, 1, 1, 1, 1},
                {0, 1, 0, 1, 1, 0, 1},
                {0, 0, 1, 0, 0, 1, 0},
                {0, 1, 0, 1, 1, 0, 1},
                {1, 0, 0, 0, 1, 1, 1},
                {0, 1, 0, 1, 0, 1, 0},
                {1, 0, 1, 0, 1, 0, 1}
        };

        for (int count = 0; count < chordProg.length - 1; count++) {
            if (commonChordProg[chordProg[count] - 1][chordProg[count + 1] - 1] == 1)
                score++;
        }

        return new Integer(score);
    }

}