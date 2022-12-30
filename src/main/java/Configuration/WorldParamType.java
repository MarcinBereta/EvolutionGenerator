package Configuration;

import mapElements.Vector2d;

public enum WorldParamType {
    MAP_HEIGHT, MAP_WIDTH, MAP_VARIANT,
    INIT_GRASS_COUNT, GRASS_ENERGY, GRASS_GROWTH_RATE, GRASS_VARIANT,
    INIT_ANIMAL_COUNT, INIT_ANIMAL_ENERGY, REPRODUCTION_ENERGY_THRESHOLD,
    REPRODUCTION_COST, MIN_MUTATION_COUNT, MAX_MUTATION_COUNT, MUTATION_VARIANT,
    ANIMAL_GENOME_LENGTH, ANIMAL_VARIANT;



    public String getKey(){
        return switch(this){
            case MAP_HEIGHT -> "MAP_HEIGHT";
            case MAP_WIDTH -> "MAP_WIDTH";
            case MAP_VARIANT -> "MAP_VARIANT";
            case INIT_GRASS_COUNT -> "INIT_GRASS_COUNT";
            case GRASS_ENERGY -> "GRASS_ENERGY";
            case GRASS_GROWTH_RATE -> "GRASS_GROWTH_RATE";
            case GRASS_VARIANT -> "GRASS_VARIANT";
            case INIT_ANIMAL_COUNT -> "INIT_ANIMAL_COUNT";
            case INIT_ANIMAL_ENERGY -> "INIT_ANIMAL_ENERGY";
            case REPRODUCTION_ENERGY_THRESHOLD -> "REPRODUCTION_ENERGY_THRESHOLD";
            case REPRODUCTION_COST -> "REPRODUCTION_COST";
            case MIN_MUTATION_COUNT -> "MIN_MUTATION_COUNT";
            case MAX_MUTATION_COUNT -> "MAX_MUTATION_COUNT";
            case MUTATION_VARIANT -> "MUTATION_VARIANT";
            case ANIMAL_GENOME_LENGTH -> "ANIMAL_GENOME_LENGTH";
            case ANIMAL_VARIANT -> "ANIMAL_VARIANT";
        };
    }

    public String toString(){
        return switch (this){
            case MAP_HEIGHT -> "Map height";
            case MAP_WIDTH -> "Map width";
            case MAP_VARIANT -> "Map variant";
            case INIT_GRASS_COUNT -> "Init grass count";
            case GRASS_ENERGY -> "Grass energy";
            case GRASS_GROWTH_RATE -> "Grass growth rate";
            case GRASS_VARIANT -> "Grass variant";
            case INIT_ANIMAL_COUNT -> "Init animal count";
            case INIT_ANIMAL_ENERGY -> "Init animal energy";
            case REPRODUCTION_ENERGY_THRESHOLD -> "Reproduction energy threshold";
            case REPRODUCTION_COST -> "Reproduction cost";
            case MIN_MUTATION_COUNT -> "Min mutation count";
            case MAX_MUTATION_COUNT -> "Max mutation count";
            case MUTATION_VARIANT -> "Mutation variant";
            case ANIMAL_GENOME_LENGTH -> "Animal genome length";
            case ANIMAL_VARIANT -> "Animal variant";
        };
    }

    public Vector2d getValueRange(){
        return switch(this){
            case MAP_VARIANT,
                    GRASS_VARIANT,
                    MUTATION_VARIANT,
                    ANIMAL_VARIANT -> new Vector2d(0, 1);
            case GRASS_ENERGY,
                    GRASS_GROWTH_RATE,
                    INIT_ANIMAL_ENERGY,
                    REPRODUCTION_ENERGY_THRESHOLD,
                    REPRODUCTION_COST,
                    MIN_MUTATION_COUNT,
                    MAX_MUTATION_COUNT,
                    ANIMAL_GENOME_LENGTH -> new Vector2d(0, 100);
            case INIT_ANIMAL_COUNT -> new Vector2d(0, 1000);
            case MAP_HEIGHT,
                    MAP_WIDTH,
                    INIT_GRASS_COUNT -> new Vector2d(0, 10000);
        };
    }

    public int getDefaultValue(){
        return switch(this){
            case MAP_HEIGHT,
                    INIT_ANIMAL_ENERGY,
                    MAP_WIDTH -> 10;
            case MAP_VARIANT,
                    GRASS_VARIANT,
                    MIN_MUTATION_COUNT,
                    MUTATION_VARIANT,
                    ANIMAL_VARIANT -> 0;
            case INIT_GRASS_COUNT,
                    REPRODUCTION_COST,
                    MAX_MUTATION_COUNT -> 2;
            case GRASS_ENERGY,
                    GRASS_GROWTH_RATE,
                    REPRODUCTION_ENERGY_THRESHOLD -> 1;
            case INIT_ANIMAL_COUNT -> 20;
            case ANIMAL_GENOME_LENGTH -> 7;
        };
    }

    private void mustBeValid(int value) throws IllegalArgumentException{
        Vector2d range = this.getValueRange();
        if (value < range.x || range.y < value){
            throw new IllegalArgumentException(this + "must be in range: " + range);
        }
    }


    public Object parse(int value) throws IllegalArgumentException{
        this.mustBeValid(value);
        return switch(this){
            case MAP_HEIGHT, MAP_WIDTH,
                    INIT_GRASS_COUNT, GRASS_ENERGY, GRASS_GROWTH_RATE,
                    INIT_ANIMAL_COUNT, INIT_ANIMAL_ENERGY, REPRODUCTION_ENERGY_THRESHOLD, REPRODUCTION_COST,
                    MIN_MUTATION_COUNT, MAX_MUTATION_COUNT, ANIMAL_GENOME_LENGTH
                    -> value;
            case MAP_VARIANT -> VariantMap.parse(value);
            case GRASS_VARIANT -> VariantGrass.parse(value);
            case MUTATION_VARIANT -> VariantMutation.parse(value);
            case ANIMAL_VARIANT -> VariantAnimal.parse(value);
        };
    }
}