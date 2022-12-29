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
    private Animal testAnimal;
    private boolean firstAnimal = true;
    private Vector2d or;
    public boolean running = true;


    private Thread engineThread;

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
    }

    public void run() {
        int i = 0;
        updateMap();
        while (running && i < 100) {
            for (Animal animal : animalsList) {
                animal.move();
            }

            LinkedList<Animal> newAnimals = map.simulateDayPass();

//           updateMap();
            for (Animal animal : newAnimals) {
                if (firstAnimal) {
                    testAnimal = animal;
                    firstAnimal = false;
                    or = animal.position;
                }
                calculateDay();
                System.out.println(animal.getPosition());
                animalsList.add(animal);
            }
//           animalsList.addAll(newAnimals);
            if (animalsList.size() <= 1) {
                System.out.println("Koniec gry");
                break;
            }
            LinkedList<Animal> nolivingAnimals = new LinkedList<>();
            for (Animal animal : animalsList) {
                if (animal.isDead()) {
                    nolivingAnimals.add(animal);
//                   System.out.println("DEAD "+ animal.getPosition());
                }
            }
//           updateMap();
            for (Animal animal : nolivingAnimals) {
                map.removeDeadAnimal(animal);
            }
            deadAnimals.addAll(nolivingAnimals);
            animalsList.removeAll(nolivingAnimals);
            map.dailyGrassChange();
            updateMap();
            i++;
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
    public void calculateDay() {
        countLifeSpan();}
    public double getAvgChildrenAmount(){
        int sum = 0;
        for (Animal animal : this.animalsList){
            sum += animal.getChildrenCount();
        }
        return (sum*1.0)/this.countAnimals();
    }
    public void stopSimulation() {
        this.running = false;

    }
    public void countLifeSpan(){
        for (Animal animal : animalsList) {
            animal.survivedDay();
        }
    }

    public double getAvgLifeSpan(){
        int sum = 0;
        for (Animal animal : this.deadAnimals){
            sum += animal.getLifeSpan();
        }
        if(this.deadAnimals.size()>0)
            return (sum*1.0)/this.deadAnimals.size();
        else
            return 0;
    }
}