package mapManager;

import mapElements.Animal;
import mapElements.MapDirection;
import mapElements.Vector2d;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SimulationEngine implements Runnable {
    AbstractWorldMap map;
    MapSettings mapSettings;
    protected LinkedList<Animal> animalsList = new LinkedList<>();
    protected LinkedList<Animal> deadAnimals = new LinkedList<>();
    private List<AnimalMoveInteface> observers = new ArrayList<>();
    public boolean running = true;

    private Thread engineThread;
    private int genomPopularity[];
    public int mostPopularGen=0;

    public SimulationEngine(AbstractWorldMap map, MapSettings mapSettings) {
        this.map = map;
        this.mapSettings = mapSettings;
        for (int i = 0; i < mapSettings.startingAnimals; i++) {
            Vector2d position = new Vector2d((int) (Math.random() * mapSettings.mapWidth), (int) (Math.random() * map.mapSettings.mapHeight));
            Animal animal = new Animal(position, map, MapDirection.NORTH, 0,
                    mapSettings, null);
            animalsList.add(animal);
            map.setStartingAnimal(animal);
        }
        genomPopularity = new int[mapSettings.genSize];
        for(Animal myanimal : animalsList){
            for(int i = 0; i < mapSettings.genSize; i++){
                genomPopularity[myanimal.gens.getAllGens()[i]] ++;
            }
        }
        getMostPopularGen();
    }

    public void run() {
        int i = 0;
        updateMap();
        while (running && i < 100) {
            for (Animal animal : animalsList) {
                animal.move();
            }
            LinkedList<Animal> newAnimals = map.simulateDayPass();
            for (Animal animal : newAnimals) {
                for(int j = 0; j < mapSettings.genSize; j++){
                    genomPopularity[animal.gens.getAllGens()[j]] ++;
                }
//                calculateDay();
                System.out.println(animal.getPosition());
                animalsList.add(animal);
            }
            if (animalsList.size() < 1) {
                System.out.println("Koniec gry");
                break;
            }
            LinkedList<Animal> nolivingAnimals = new LinkedList<>();
            for (Animal animal : animalsList) {
                if (animal.isDead()) {
                    for(int j = 0; j < mapSettings.genSize; j++){
                        genomPopularity[animal.gens.getAllGens()[j]] --;
                    }
                    nolivingAnimals.add(animal);
                }
            }
            for (Animal animal : nolivingAnimals) {
                map.removeDeadAnimal(animal);
            }
            getMostPopularGen();
            deadAnimals.addAll(nolivingAnimals);
            animalsList.removeAll(nolivingAnimals);
            map.dailyGrassChange();
            updateMap();
            i++;
        }
    }
    public void getMostPopularGen(){
        int max = 0;
        for(int i = 0; i < mapSettings.genSize; i++){
            if(genomPopularity[i] > max){
                max = genomPopularity[i];
                mostPopularGen = i;
            }
        }
    }

    private void updateMap() {
        for (AnimalMoveInteface observer : observers) {
            observer.move();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("The simulation has stopped");
        }
    }

    public void addObserver(AnimalMoveInteface observer) {
        observers.add(observer);
    }

    public void startSimulation() {
        this.running = true;
        this.engineThread = new Thread(this);
        this.engineThread.start();
    }

    public int mostPopularGen(){
        return mostPopularGen;
    }
    public int countAnimals() {
        return this.animalsList.size();
    }
    public double getAvgEnergy(){
        int sum = 0;
        for (Animal animal : this.animalsList){
            sum += animal.getEnergy();
        }
        return (sum*1.0)/this.countAnimals();
    }

    public void stopSimulation() {
        this.running = false;

    }

    public double getAvgLifeSpan(){
        int sum = 0;
        for (Animal animal : this.deadAnimals){
            sum += animal.getAge();
        }
        if(this.deadAnimals.size()>0)
            return (sum*1.0)/this.deadAnimals.size();
        else
            return 0;
    }
}