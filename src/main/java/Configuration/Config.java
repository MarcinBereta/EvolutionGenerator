package Configuration;

public class Config {
    public static final String CONFIG_DIR_PATH = "./ConfigFiles";

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
            WorldParamType.MOVE_TYPE,
            WorldParamType.DAY_COST,
            WorldParamType.MAX_GENS,
            WorldParamType.MIN_GENS
    };
}