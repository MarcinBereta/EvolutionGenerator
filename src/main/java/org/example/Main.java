package org.example;

import mapManager.*;
import mapElements.*;

public class Main {
    public static void main(String[] args) {
        MapSettings mapSettings = new MapSettings();
        AbstractWorldMap map = new Hell(mapSettings);
        SimulationEngine engine = new SimulationEngine(map, mapSettings);
        engine.run();


    }
}