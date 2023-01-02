package Configuration;

public class Config {
    public static final int MAX_INT = 2147483647;
    public static final String CONFIG_DIR_PATH = "./ConfigFiles";

    //CSV File parameters
    public static final WorldParamType[] CONFIG_FILE_STRUCTURE = {
            WorldParamType.MAP_HEIGHT,
            WorldParamType.MAP_WIDTH,
            WorldParamType.MAP_VARIANT,
            WorldParamType.STARTING_GRASS,
            WorldParamType.PLANT_PROFIT,
            WorldParamType.GRASS_GROWTH_RATE,
            WorldParamType.GRASS_VARIANT,
            WorldParamType.STARTING_ANIMALS,
            WorldParamType.START_ENERGY,
            WorldParamType.REQUIRED_COPULATION_ENERGY,
            WorldParamType.REPRODUCTION_COST,
            WorldParamType.MUTATION_VARIANT,
            WorldParamType.ANIMAL_GEN_SIZE,
            WorldParamType.ANIMAL_VARIANT,
            WorldParamType.DAY_COST,
            WorldParamType.MAX_GENS,
            WorldParamType.MIN_GENS
    };
}