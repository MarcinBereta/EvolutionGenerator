package Configuration;

public class ConfigFileStructure {
    public static final String CONFIG_PATH = "./SimulationFiles/ConfigFiles";

    public static final WorldTypeParamaters[] CONFIG_FILE_STRUCTURE = {
            WorldTypeParamaters.MAP_HEIGHT,
            WorldTypeParamaters.MAP_WIDTH,
            WorldTypeParamaters.MAP_TYPE,
            WorldTypeParamaters.STARTING_GRASS,
            WorldTypeParamaters.PLANT_PROFIT,
            WorldTypeParamaters.DAILY_GRASS,
            WorldTypeParamaters.GRASS_TYPE,
            WorldTypeParamaters.STARTING_ANIMALS,
            WorldTypeParamaters.START_ENERGY,
            WorldTypeParamaters.REQUIRED_COPULATION_ENERGY,
            WorldTypeParamaters.REPRODUCTION_COST,
            WorldTypeParamaters.GEN_TYPE,
            WorldTypeParamaters.ANIMAL_GEN_SIZE,
            WorldTypeParamaters.MOVE_TYPE,
            WorldTypeParamaters.DAY_COST,
            WorldTypeParamaters.MAX_GENS,
            WorldTypeParamaters.MIN_GENS
    };
}