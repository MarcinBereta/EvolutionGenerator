package Configuration;

import Configuration.Types.GenTypes;
import Configuration.Types.GrassType;
import Configuration.Types.MapTypes;
import Configuration.Types.MoveType;
import mapElements.Vector2d;

public enum WorldTypeParamaters {
    MAP_HEIGHT, MAP_WIDTH, MAP_TYPE,
    STARTING_GRASS, PLANT_PROFIT, DAILY_GRASS, GRASS_TYPE,
    STARTING_ANIMALS, START_ENERGY, REQUIRED_COPULATION_ENERGY,
    REPRODUCTION_COST,  GEN_TYPE,
    ANIMAL_GEN_SIZE, MOVE_TYPE, DAY_COST,
    MAX_GENS, MIN_GENS;



    public String getKey(){
        return switch(this){
            case MAP_HEIGHT -> "MAP_HEIGHT";
            case MAP_WIDTH -> "MAP_WIDTH";
            case MAP_TYPE -> "MAP_TYPE";
            case STARTING_GRASS -> "STARTING_GRASS";
            case PLANT_PROFIT -> "PLANT_PROFIT";
            case DAILY_GRASS -> "DAILY_GRASS";
            case GRASS_TYPE -> "GRASS_TYPE";
            case STARTING_ANIMALS -> "STARTING_ANIMALS";
            case START_ENERGY -> "START_ENERGY";
            case REQUIRED_COPULATION_ENERGY -> "REQUIRED_COPULATION_ENERGY";
            case REPRODUCTION_COST -> "REPRODUCTION_COST";
            case GEN_TYPE -> "GEN_TYPE";
            case ANIMAL_GEN_SIZE -> "ANIMAL_GEN_SIZE";
            case MOVE_TYPE -> "MOVE_TYPE";
            case DAY_COST -> "DAY_COST";
            case MAX_GENS -> "MAX_GENS";
            case MIN_GENS -> "MIN_GENS";
        };
    }

    public String toString(){
        return switch (this){
            case MAP_HEIGHT -> "Map height";
            case MAP_WIDTH -> "Map width";
            case MAP_TYPE -> "Map type";
            case STARTING_GRASS -> "Starting grass";
            case PLANT_PROFIT -> "Plant profit";
            case DAILY_GRASS -> "Daily grass";
            case GRASS_TYPE -> "Grass type";
            case STARTING_ANIMALS -> "Starting animals";
            case START_ENERGY -> "Starting energy";
            case REQUIRED_COPULATION_ENERGY -> "Required copulation energy";
            case REPRODUCTION_COST -> "Reproduction cost";
            case GEN_TYPE -> "Genome type";
            case ANIMAL_GEN_SIZE -> "Animal genome length";
            case MOVE_TYPE -> "Move type";
            case DAY_COST -> "Day cost";
            case MAX_GENS -> "Max gens changes";
            case MIN_GENS -> "Min gens Changes";
        };
    }

    public Vector2d getValueRange(){
        return switch(this){
            case MAP_TYPE,
                    GRASS_TYPE,
                    GEN_TYPE,
                    MOVE_TYPE -> new Vector2d(0, 1);
            case PLANT_PROFIT,
                    DAILY_GRASS,
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
            case MAP_TYPE,
                    GRASS_TYPE,
                    GEN_TYPE,
                    MOVE_TYPE -> 0;
            case STARTING_GRASS,
                    MIN_GENS,
                    REPRODUCTION_COST-> 2;
            case PLANT_PROFIT,
                    DAILY_GRASS,
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
                    STARTING_GRASS, PLANT_PROFIT, DAILY_GRASS,
                    STARTING_ANIMALS, START_ENERGY, REPRODUCTION_COST,REQUIRED_COPULATION_ENERGY,
                    ANIMAL_GEN_SIZE, DAY_COST, MIN_GENS,MAX_GENS
                    -> value;
            case MAP_TYPE -> MapTypes.parse(value);
            case GRASS_TYPE -> GrassType.parse(value);
            case GEN_TYPE -> GenTypes.parse(value);
            case MOVE_TYPE -> MoveType.parse(value);
        };
    }
}