package mapManager;

import mapElements.MapEffects;

public class MapSettings {
    public int mapWidth = 10;
    public int mapHeight = 10;
    public int plantProfit = 10;
    public int dayCost = 5;
    public int startEnergy = 20;
    public int copulationEnergy = 5;
    public int startingAnimals = 5;
    public int dailyGrass = 10;
    public int startGrass = 10;
    public MapEffects jungleType = MapEffects.TOXIC;
    //GEN SECTION
    public MapEffects moveType = MapEffects.BITOFMADDNESS;
    public MapEffects genType = MapEffects.FULLRANDOM;
    public int genSize = 32;

    public MapSettings() {
    }
    public MapSettings(
            int mapWidth, int mapHeight, int plantProfit, int dayCost, int startEnergy, int copulationEnergy,
            int jungleSize, int dailyGrass, int startGrass, int startingAnimals,
            MapEffects jungleType, MapEffects moveType, int genSize
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
            int jungleSize, int dailyGrass, MapEffects jungleType, MapEffects moveType, int genSize
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
    }
}