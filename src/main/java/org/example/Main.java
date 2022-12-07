package org.example;
import mapManager.*;
import mapElements.*;
public class Main {
    public static void main(String[] args) {
        int startAnimals = 10;
        int mapWidth = 40;
        int mapHeight = 40;
        int startEnergy = 10;
        int moveEnergy = 1;
        int grassEnergy = 5;
        int copulationEnergy = 5;
        int grassPerDay = 5;
        int genSize = 32;
        int randomGens = 1;
        int jungleType = 1;
        int edgeType = 1;
        int startGrass = 5;
        int moveOption = 1;
        IWorldMap map = new IWorldMap(mapWidth, mapHeight, grassEnergy, moveEnergy, startEnergy, copulationEnergy,
                jungleType, edgeType, startGrass, moveOption, grassPerDay, randomGens, genSize);
        SimulationEngine engine = new SimulationEngine(map, startAnimals);
        engine.run();


    }
}