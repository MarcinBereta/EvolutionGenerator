package mapManager;

import Configuration.WorldParamType;

import java.util.Map;

public class World {
    IMap map;
    Map<WorldParamType, Object> worldParams;


    public World (Map<WorldParamType, Object> worldParams, Integer epochCount, Double epochDuration) throws IllegalArgumentException{
        this.worldParams = worldParams;
    }

    public World (Map<WorldParamType, Object> worldParams, Integer epochCount, Double epochDuration, String csvFilePath) throws IllegalArgumentException{
        this.worldParams = worldParams;
    }

}