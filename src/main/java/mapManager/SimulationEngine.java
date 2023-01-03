package mapManager;

import mapElements.Animal;
import mapElements.MapDirection;
import mapElements.Vector2d;

import java.util.ArrayList;
import java.util.Arrays;
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
    private LinkedList<String> genomPopularity= new LinkedList<>();
    public String mostPopularGen="";

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

        for(Animal myanimal : animalsList){
            genomPopularity.add(Arrays.toString(myanimal.gens.getAllGens()));
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
                genomPopularity.add(Arrays.toString(animal.gens.getAllGens()));
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
                    String removal = Arrays.toString(animal.gens.getAllGens());
                    int index = 0;
                    for(String gen : genomPopularity){
                        if(gen.equals(removal)){
                            genomPopularity.remove(index);
                            break;
                        }
                        index++;
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
    public String getMostPopularGen(){
        int max = 0;
        String[] tempArr = new String[genomPopularity.size()];
        tempArr = genomPopularity.toArray(tempArr);
        for(int i = 0; i < tempArr.length; i++){
            int count = 0;
            for(int j = 0; j < tempArr.length; j++){
                if(tempArr[i].equals(tempArr[j])){
                    count++;
                }
            }
            if(count > max){
                max = count;
                mostPopularGen = tempArr[i];
            }
        }
        return mostPopularGen;
    }
    public int oneGenomePlease(){
        return Integer.parseInt(getMostPopularGen().split(",")[0].replace("[", ""));
    }
    public int allpopulargenome(){
        String input = getMostPopularGen();
        input = input.replaceAll("^\\[|\\]$", "");
        String[] parts = input.split(",");
        int result = 0;
        for (String part : parts) {
            part = part.trim();
            if (part.matches("\\d+")) {
                result = result * 10 + Integer.parseInt(part);
            }
        }
        return result;
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

    public LinkedList<String> animalsWithGenom(){
        LinkedList<String> animalsWithGenom = new LinkedList<>();
        for(Animal animal : animalsList){
            if(Arrays.toString(animal.gens.getAllGens()).equals(mostPopularGen)){
                animalsWithGenom.add("Animal at position " + animal.getPosition() + "\n");
            }
        }
        return animalsWithGenom;
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