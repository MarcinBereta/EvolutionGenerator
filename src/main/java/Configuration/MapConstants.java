package Configuration;

public class MapConstants {
    private final int MAP_HEIGHT;
    private final int MAP_WIDTH;

    private final int INIT_GRASS_COUNT;
    private final int GRASS_ENERGY;
    private final int GRASS_GROWTH_PER_DAY;


    public MapConstants(int mapHeight, int mapWidth, int initGrassCount, int grassEnergy, int GrassGrowthPerDay){
        this.MAP_HEIGHT=mapHeight;
        this.MAP_WIDTH=mapWidth;
        this.INIT_GRASS_COUNT=initGrassCount;
        this.GRASS_ENERGY=grassEnergy;
        this.GRASS_GROWTH_PER_DAY=GrassGrowthPerDay;
    }
    public int get(WorldParamType paramType) throws IllegalArgumentException{
        return switch(paramType){
            case MAP_HEIGHT -> this.MAP_HEIGHT;
            case MAP_WIDTH -> this.MAP_WIDTH;
            case INIT_GRASS_COUNT -> this.INIT_GRASS_COUNT;
            case GRASS_ENERGY -> this.GRASS_ENERGY;
            case GRASS_GROWTH_RATE -> this.GRASS_GROWTH_PER_DAY;
            default -> throw new IllegalArgumentException();
        };
    }

}