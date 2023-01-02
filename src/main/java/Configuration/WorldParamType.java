package Configuration;

import mapElements.Vector2d;

public enum WorldParamType {
    MAP_HEIGHT, MAP_WIDTH, MAP_VARIANT,
    STARTING_GRASS, PLANT_PROFIT, GRASS_GROWTH_RATE, GRASS_VARIANT,
    STARTING_ANIMALS, START_ENERGY, REQUIRED_COPULATION_ENERGY,
    REPRODUCTION_COST,  MUTATION_VARIANT,
    ANIMAL_GEN_SIZE, ANIMAL_VARIANT, DAY_COST,
    MAX_GENS, MIN_GENS;



    public String getKey(){
        return switch(this){
            case MAP_HEIGHT -> "MAP_HEIGHT";
            case MAP_WIDTH -> "MAP_WIDTH";
            case MAP_VARIANT -> "MAP_VARIANT";
            case STARTING_GRASS -> "STARTING_GRASS";
            case PLANT_PROFIT -> "PLANT_PROFIT";
            case GRASS_GROWTH_RATE -> "GRASS_GROWTH_RATE";
            case GRASS_VARIANT -> "GRASS_VARIANT";
            case STARTING_ANIMALS -> "STARTING_ANIMALS";
            case START_ENERGY -> "START_ENERGY";
            case REQUIRED_COPULATION_ENERGY -> "REQUIRED_COPULATION_ENERGY";
            case REPRODUCTION_COST -> "REPRODUCTION_COST";
            case MUTATION_VARIANT -> "MUTATION_VARIANT";
            case ANIMAL_GEN_SIZE -> "ANIMAL_GEN_SIZE";
            case ANIMAL_VARIANT -> "ANIMAL_VARIANT";
            case DAY_COST -> "DAY_COST";
            case MAX_GENS -> "MAX_GENS";
            case MIN_GENS -> "MIN_GENS";
        };
    }

    public String toString(){
        return switch (this){
            case MAP_HEIGHT -> "Map height";
            case MAP_WIDTH -> "Map width";
            case MAP_VARIANT -> "Map variant";
            case STARTING_GRASS -> "Starting grass";
            case PLANT_PROFIT -> "Plant profit";
            case GRASS_GROWTH_RATE -> "Grass growth rate";
            case GRASS_VARIANT -> "Grass variant";
            case STARTING_ANIMALS -> "Starting animals";
            case START_ENERGY -> "Starting energy";
            case REQUIRED_COPULATION_ENERGY -> "Required copulation energy";
            case REPRODUCTION_COST -> "Reproduction cost";
            case MUTATION_VARIANT -> "Mutation variant";
            case ANIMAL_GEN_SIZE -> "Animal genome length";
            case ANIMAL_VARIANT -> "Animal variant";
            case DAY_COST -> "Day cost";
            case MAX_GENS -> "Max gens changes";
            case MIN_GENS -> "Min gens Changes";
        };
    }

    public Vector2d getValueRange(){
        return switch(this){
            case MAP_VARIANT,
                    GRASS_VARIANT,
                    MUTATION_VARIANT,
                    ANIMAL_VARIANT -> new Vector2d(0, 1);
            case PLANT_PROFIT,
                    GRASS_GROWTH_RATE,
                    START_ENERGY,
                    REQUIRED_COPULATION_ENERGY,
                    REPRODUCTION_COST,
                    DAY_COST,
                    MIN_GENS,MAX_GENS,
                    ANIMAL_GEN_SIZE -> new Vector2d(0, 100);
            case STARTING_ANIMALS -> new Vector2d(0, 1000);
            case MAP_HEIGHT,
                    MAP_WIDTH,
                    STARTING_GRASS -> new Vector2d(0, 10000);
        };
    }

    public int getDefaultValue(){
        return switch(this){
            case MAP_HEIGHT,
                    START_ENERGY,
                    REQUIRED_COPULATION_ENERGY,
                    MAP_WIDTH -> 10;
            case MAP_VARIANT,
                    GRASS_VARIANT,
                    MUTATION_VARIANT,
                    ANIMAL_VARIANT -> 0;
            case STARTING_GRASS,
                    MIN_GENS,
                    REPRODUCTION_COST-> 2;
            case PLANT_PROFIT,
                    GRASS_GROWTH_RATE,
                    DAY_COST-> 1;
            case STARTING_ANIMALS -> 20;
            case ANIMAL_GEN_SIZE,MAX_GENS -> 7;
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
                    STARTING_GRASS, PLANT_PROFIT, GRASS_GROWTH_RATE,
                    STARTING_ANIMALS, START_ENERGY, REPRODUCTION_COST,REQUIRED_COPULATION_ENERGY,
                    ANIMAL_GEN_SIZE, DAY_COST, MIN_GENS,MAX_GENS
                    -> value;
            case MAP_VARIANT -> VariantMap.parse(value);
            case GRASS_VARIANT -> VariantGrass.parse(value);
            case MUTATION_VARIANT -> VariantMutation.parse(value);
            case ANIMAL_VARIANT -> VariantAnimal.parse(value);
        };
    }
}