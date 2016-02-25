package MusicAPI.GeneticAlgorithm;

import java.util.*;

import org.jenetics.*;
import org.jenetics.util.*;
import org.jenetics.engine.Engine;

import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import static org.jenetics.engine.limit.bySteadyFitness;

public class GeneticAlgorithm {

    public static void enumgeneticAlgorithm(ArrayList<Enum> myEnum, int length, FitnessFunction fitFunctionInstance) {

        geneticAlgorithm(0, myEnum.size() - 1, length, fitFunctionInstance);

    }

    public static void geneticAlgorithm(int minVal, int maxVal, int length, FitnessFunction fitFunctionInstance) {

        final Engine<IntegerGene, Integer> engine = Engine
                .builder(
                        fitFunctionInstance::fitnessFunction,
                        IntegerChromosome.of(minVal, maxVal, length))
                .optimize(Optimize.MAXIMUM)
                .maximalPhenotypeAge(11)
                .populationSize(500)
                .alterers(
                        new Mutator<>(0.115),
                        new SinglePointCrossover<>(0.16))
                .build();

        final Phenotype<IntegerGene, Integer> best =
                engine.stream()
                        .limit(bySteadyFitness(15))
                        .limit(250)
                        .collect(toBestPhenotype());

        System.out.println(best);
    }

    public static ArrayList<Enum> integertoEnum(final Genotype<IntegerGene> gt, ArrayList<Enum> myEnum) {
        final int[] intSet = gt.getChromosome().toSeq().stream().mapToInt(IntegerGene::getAllele).toArray();
        ArrayList<Enum> enumList = new ArrayList<Enum>();
        for (int count = 0; count < intSet.length; count++) {
            enumList.add(myEnum.get(intSet[count]));
        }

        return enumList;
    }

    public static final int[] genotypeToIntArray(final Genotype<IntegerGene> gt) {
        return gt.getChromosome().toSeq().stream().mapToInt(IntegerGene::intValue).toArray();
    }
}