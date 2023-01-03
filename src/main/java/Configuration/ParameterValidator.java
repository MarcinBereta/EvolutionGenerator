package Configuration;

import gui.SimulationWindow;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ParameterValidator {
    Map<WorldTypeParamaters, Object> worldParams;

    public ParameterValidator(String configFileName) throws FileNotFoundException, IllegalArgumentException{
        worldParams = loadWorldParamaters(configFileName);
        checkParamaters();
        new SimulationWindow(worldParams);
    }

    private Map<WorldTypeParamaters, Object> loadWorldParamaters(String configFileName) throws FileNotFoundException, IllegalArgumentException{
        List<String> fileContent = getFileContent(ConfigFileStructure.CONFIG_PATH  + '/' + configFileName);

        int len = ConfigFileStructure.CONFIG_FILE_STRUCTURE.length;
        if (fileContent.size() < len){
            throw new IllegalArgumentException("Config file has to little arguments, create new config file");
        }
        if (fileContent.size() > len){
            throw new IllegalArgumentException("Config file has much arguments");
        }

        Map<WorldTypeParamaters, Object> paramValues = new HashMap<>();
        for(int i = 0; i < len; i++){
            String[] parts = fileContent.get(i).split(" ");
            WorldTypeParamaters type = ConfigFileStructure.CONFIG_FILE_STRUCTURE[i];
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

    public static String startNewSimulation(String ConfigFileName){
        try{
            new ParameterValidator(ConfigFileName);
            return "";
        }
        catch (IllegalArgumentException | FileNotFoundException e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    private void checkParamaters() throws IllegalArgumentException {
        WorldTypeParamaters[] mustBePositiveParams = {
                WorldTypeParamaters.MAP_HEIGHT,
                WorldTypeParamaters.MAP_WIDTH,
                WorldTypeParamaters.STARTING_GRASS,
                WorldTypeParamaters.PLANT_PROFIT,
                WorldTypeParamaters.DAILY_GRASS,
                WorldTypeParamaters.STARTING_ANIMALS,
                WorldTypeParamaters.START_ENERGY,
                WorldTypeParamaters.REQUIRED_COPULATION_ENERGY,
                WorldTypeParamaters.REPRODUCTION_COST,
                WorldTypeParamaters.MIN_GENS,
                WorldTypeParamaters.MAX_GENS,
                WorldTypeParamaters.ANIMAL_GEN_SIZE
        };

        for (WorldTypeParamaters param : mustBePositiveParams) {
            mustBePositive(param);

        }

        Integer mapHeight = (Integer) getParamatersValue(WorldTypeParamaters.MAP_HEIGHT);
        Integer mapWidth = (Integer) getParamatersValue(WorldTypeParamaters.MAP_WIDTH);
        Integer mapCellCountVal = mapHeight * mapWidth;
        String mapCellCountDesc = WorldTypeParamaters.MAP_WIDTH + " * " + WorldTypeParamaters.MAP_HEIGHT;

        mustBeLower(WorldTypeParamaters.STARTING_GRASS, mapCellCountVal, mapCellCountDesc);
        mustBeLower(WorldTypeParamaters.DAILY_GRASS, mapCellCountVal, mapCellCountDesc);
        mustBeLower(
                WorldTypeParamaters.MIN_GENS,
                (Integer) getParamatersValue(WorldTypeParamaters.MAX_GENS) + 1,
                "" + WorldTypeParamaters.MAX_GENS
        );
        mustBeLower(
                WorldTypeParamaters.MAX_GENS,
                (Integer) getParamatersValue(WorldTypeParamaters.ANIMAL_GEN_SIZE),
                "" + WorldTypeParamaters.MOVE_TYPE);
    }

    private Object getParamatersValue(WorldTypeParamaters paramType) throws IllegalArgumentException {
        Object val = worldParams.get(paramType);
        if (val == null)
            throw new IllegalArgumentException("no value for " + paramType + "provided");
        return val;
    }

    private void mustBePositive(WorldTypeParamaters paramType) throws IllegalArgumentException{
        Integer val = (Integer) getParamatersValue(paramType);
        if(val < 0)
            throw new IllegalArgumentException(paramType + "must be positive");
    }

    private void mustBeLower(WorldTypeParamaters paramType, Integer limit, String limitDesc) throws IllegalArgumentException{
        Integer val = (Integer) getParamatersValue(paramType);
        if(limit < val)
            throw new IllegalArgumentException(paramType + "cannot be greater than" + limitDesc);
    }
}
