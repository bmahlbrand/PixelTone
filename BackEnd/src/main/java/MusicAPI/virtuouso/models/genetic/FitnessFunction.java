package MusicAPI.virtuouso.models.genetic;

import org.jenetics.Genotype;
import org.jenetics.IntegerGene;

public interface FitnessFunction {
    Integer fitnessFunction(final Genotype<IntegerGene> gt);
}