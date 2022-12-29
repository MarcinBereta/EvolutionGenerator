package Configuration;

import gui.SimulationWindow;
import mapManager.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ParameterValidator {
    World world;
    Map<WorldParamType, Object> worldParams;

    public ParameterValidator(String configFileName, Integer epochCount, Double epochDuration) throws FileNotFoundException, IllegalArgumentException{
        worldParams = loadParamWorld(configFileName);
        checkConsistency();
//        world = new World(worldParams, epochCount, epochDuration);
        new SimulationWindow(worldParams);
    }


    public ParameterValidator(String configFileName, Integer epochCount, Double epochDuration, String csvFilePath)
            throws FileNotFoundException, IllegalArgumentException{
        worldParams = loadParamWorld(configFileName);
        checkConsistency();
//        world = new World(worldParams, epochCount, epochDuration, csvFilePath);
        new SimulationWindow(worldParams);
    }

    private Map<WorldParamType, Object> loadParamWorld(String configFileName) throws FileNotFoundException, IllegalArgumentException{
        List<String> fileContent = getFileContent(Config.CONFIG_DIR_PATH  + '/' + configFileName);

        int len = Config.CONFIG_FILE_STRUCTURE.length;
        if (fileContent.size() < len){
            throw new IllegalArgumentException("Config file has to little arguments");
        }
        if (fileContent.size() > len){
            throw new IllegalArgumentException("Config file has much little arguments");
        }

        Map<WorldParamType, Object> paramValues = new HashMap<>();
        for(int i = 0; i < len; i++){
            String[] parts = fileContent.get(i).split(" ");
            WorldParamType type = Config.CONFIG_FILE_STRUCTURE[i];
            if (parts.length != 2){
                throw new IllegalArgumentException("Invalid input for " + type);
            }
            if (!Objects.equals(parts[0], type.getKey())){
                throw new IllegalArgumentException("Invalid key for " + type);
            }
            int value;
            try{
                value = Integer.parseInt(parts[1]);
            }
            catch (NumberFormatException e){
                throw new IllegalArgumentException("Invalid input for " + type);
            }
            Object parsedValue = type.parse(value);
            paramValues.put(type, parsedValue);
        }
        return paramValues;
    }

    private List<String> getFileContent(String filePath) throws FileNotFoundException{
        List<String> lines = new ArrayList<>();
        File myObj = new File(filePath);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            lines.add(data);
        }
        myReader.close();
        return lines;
    }

    public static String startNewSimulation(String ConfigFileName, Integer epochCount, Double epochDuration){
        try{
            new ParameterValidator(ConfigFileName, epochCount, epochDuration);
            return "";
        }
        catch (IllegalArgumentException | FileNotFoundException e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public static String startNewSimulation(
            String ConfigFileName, String csvFilePath, Integer epochCount, Double epochDuration){
        try{
            new ParameterValidator(ConfigFileName, epochCount, epochDuration,  csvFilePath);
            return "";
        }
        catch (IllegalArgumentException | FileNotFoundException e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }


    private void checkConsistency() throws IllegalArgumentException{
        WorldParamType[] mustBePositiveParams = {
                WorldParamType.MAP_HEIGHT,
                WorldParamType.MAP_WIDTH,
                WorldParamType.INIT_GRASS_COUNT,
                WorldParamType.GRASS_ENERGY,
                WorldParamType.GRASS_GROWTH_RATE,
                WorldParamType.INIT_ANIMAL_COUNT,
                WorldParamType.INIT_ANIMAL_ENERGY,
                WorldParamType.REPRODUCTION_ENERGY_THRESHOLD,
                WorldParamType.REPRODUCTION_COST,
                WorldParamType.MIN_MUTATION_COUNT,
                WorldParamType.MAX_MUTATION_COUNT,
                WorldParamType.ANIMAL_GENOME_LENGTH,
        };

        for (WorldParamType param : mustBePositiveParams){
            mustBePositive(param);
        }

        Integer mapHeight = (Integer) getParamValue(WorldParamType.MAP_HEIGHT);
        Integer mapWidth = (Integer) getParamValue(WorldParamType.MAP_WIDTH);
        Integer mapCellCountVal = mapHeight * mapWidth;
        String mapCellCountDesc = WorldParamType.MAP_WIDTH + " * " + WorldParamType.MAP_HEIGHT;

//        INIT_PLANT_COUNT < MAP_HEIGHT * MAP_WIDTH
        mustBeLower(WorldParamType.INIT_GRASS_COUNT, mapCellCountVal, mapCellCountDesc);

//        PLANT_GROWTH_RATE < MAP_HEIGHT * MAP_WIDTH
        mustBeLower(WorldParamType.GRASS_GROWTH_RATE, mapCellCountVal, mapCellCountDesc);

//        MIN_MUTATION_COUNT < MAX_MUTATION_COUNT + 1
        mustBeLower(
                WorldParamType.MIN_MUTATION_COUNT,
                (Integer)getParamValue(WorldParamType.MAX_MUTATION_COUNT) + 1,
                "" + WorldParamType.MAX_MUTATION_COUNT
        );

//        MAX_MUTATION_COUNT < ANIMAL_GENOME_LENGTH
        mustBeLower(
                WorldParamType.MIN_MUTATION_COUNT,
                (Integer) getParamValue(WorldParamType.ANIMAL_GENOME_LENGTH),
                "" + WorldParamType.ANIMAL_GENOME_LENGTH);
    }

    private Object getParamValue(WorldParamType paramType) throws IllegalArgumentException {
        Object val = worldParams.get(paramType);
        if (val == null)
            throw new IllegalArgumentException("no value for " + paramType + "provided");
        return val;
    }

    private void mustBePositive(WorldParamType paramType) throws IllegalArgumentException{
        Integer val = (Integer) getParamValue(paramType);
        if(val < 0)
            throw new IllegalArgumentException(paramType + "must be positive");
    }

    private void mustBeLower(WorldParamType paramType, Integer limit, String limitDesc) throws IllegalArgumentException{
        Integer val = (Integer) getParamValue(paramType);
        if(limit < val)
            throw new IllegalArgumentException(paramType + "cannot be greater than" + limitDesc);
    }
}
