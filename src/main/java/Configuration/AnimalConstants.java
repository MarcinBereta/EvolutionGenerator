package Configuration;

/**
 * unmodifiable animal constants data structure ('map'),
 * one object of that class can be shared between many instances of Animal class
 */
public class AnimalConstants {
    private final int GRASS_ENERGY;
    private final int REPRODUCTION_ENERGY_THRESHOLD;
    private final int REPRODUCTION_COST;
    private final int MIN_MUTATION_COUNT;
    private final int MAX_MUTATION_COUNT;
    private final int ANIMAL_GENOME_LENGTH;

    public AnimalConstants(int plantEnergy, int reproductionEnergyThreshold, int reproductionCost,
                           int minMutationCount, int maxMutationCount, int animalGenomeLength) {
        GRASS_ENERGY = plantEnergy;
        REPRODUCTION_ENERGY_THRESHOLD = reproductionEnergyThreshold;
        REPRODUCTION_COST = reproductionCost;
        MIN_MUTATION_COUNT = minMutationCount;
        MAX_MUTATION_COUNT = maxMutationCount;
        ANIMAL_GENOME_LENGTH = animalGenomeLength;
    }

    public int get(WorldParamType paramType) throws IllegalArgumentException {
        return switch (paramType) {
            case PLANT_PROFIT -> this.GRASS_ENERGY;
            case REQUIRED_COPULATION_ENERGY -> this.REPRODUCTION_ENERGY_THRESHOLD;
            case REPRODUCTION_COST -> this.REPRODUCTION_COST;
            case MIN_GENS -> this.MIN_MUTATION_COUNT;
            case MAX_GENS -> this.MAX_MUTATION_COUNT;
            case ANIMAL_GEN_SIZE -> this.ANIMAL_GENOME_LENGTH;
            default -> throw new IllegalArgumentException();
        };
    }
}