package mapManager;

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
    public int requiredCopulationEnergy = 21;
    public int maxGens = 5;
    public int minGens = 4;
    public MapSettings() {
    }
    public MapSettings(
            int mapWidth, int mapHeight, int plantProfit, int dayCost, int startEnergy, int copulationEnergy,
            int jungleSize, int dailyGrass, int startGrass, int startingAnimals,
            MapEffects jungleType, MapEffects moveType, int genSize, MapEffects genType, int requiredCopulationEnergy, int maxGens, int minGens
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
        this.genType = genType;
        this.genSize = genSize;
        this.startGrass = startGrass;
        this.startingAnimals = startingAnimals;
        this.requiredCopulationEnergy = requiredCopulationEnergy;
        this.minGens = minGens;
        this.maxGens = maxGens;
    }

    public void updateMapSettings(
            int mapWidth, int mapHeight, int plantProfit, int dayCost, int startEnergy, int copulationEnergy,
            int jungleSize, int dailyGrass, MapEffects jungleType, MapEffects moveType, int genSize,MapEffects genType, int requiredCopulationEnergy
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
        this.genType = genType;
        this.requiredCopulationEnergy = requiredCopulationEnergy;
    }



}
