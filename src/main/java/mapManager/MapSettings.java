package mapManager;

import Configuration.VariantAnimal;
import Configuration.VariantGrass;
import Configuration.VariantMap;
import Configuration.VariantMutation;
import mapElements.MapEffects;

public class MapSettings {
    public int mapWidth = 15;
    public int mapHeight = 15;
    public int plantProfit = 3;
    public int dayCost = 2;
    public int startEnergy = 20;
    public int copulationEnergy = 10;
    public int startingAnimals = 10;
    public int dailyGrass = 5;
    public int startGrass = 5;
    public MapEffects jungleType = MapEffects.TOXIC;
    //GEN SECTION
    public MapEffects moveType = MapEffects.BITOFMADDNESS;
    public MapEffects genType = MapEffects.FULLRANDOM;
    public int genSize = 32;
    public VariantMap variantmap;
    public VariantAnimal variantanimal;
    public VariantGrass variantgrass;
    public VariantMutation variantmutation;
    public MapSettings() {
    }
    public MapSettings(
            int mapWidth, int mapHeight, int plantProfit, int dayCost, int startEnergy, int copulationEnergy,
            int jungleSize, int dailyGrass, int startGrass, int startingAnimals,
            MapEffects jungleType, MapEffects moveType, int genSize, VariantMap variantmap,
VariantAnimal variantanimal,
VariantGrass variantgrass,
 VariantMutation variantmutation
    ){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.plantProfit = plantProfit;
        this.dayCost = dayCost;
        this.startEnergy = startEnergy;
        this.copulationEnergy = copulationEnergy;
        this.dailyGrass = dailyGrass;
        this.jungleType = jungleType;
        this.moveType = moveType;
        this.genSize = genSize;
        this.startGrass = startGrass;
        this.startingAnimals = startingAnimals;
    }

    public void updateMapSettings(
            int mapWidth, int mapHeight, int plantProfit, int dayCost, int startEnergy, int copulationEnergy,
            int jungleSize, int dailyGrass, MapEffects jungleType, MapEffects moveType, int genSize, VariantMap variantmap,
            VariantAnimal variantanimal,
            VariantGrass variantgrass,
            VariantMutation variantmutation    ){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.plantProfit = plantProfit;
        this.dayCost = dayCost;
        this.startEnergy = startEnergy;
        this.copulationEnergy = copulationEnergy;
        this.dailyGrass = dailyGrass;
        this.jungleType = jungleType;
        this.moveType = moveType;
        this.genSize = genSize;
//        this.variantmap = variantmap;
//        this.variantanimal = variantanimal;
//        this.variantmutation = variantmutation;
//        this.variantgrass = variantgrass;
    }



}
