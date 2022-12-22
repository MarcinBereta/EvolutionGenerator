package mapManager;

import mapElements.Animal;
import mapElements.MapDirection;
import mapElements.Vector2d;

import java.io.InvalidObjectException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class SimulationEngine {
    AbstractWorldMap map;
    MapSettings mapSettings;
    protected LinkedList<Animal> animalsList = new LinkedList<>();
    protected LinkedList<Animal> deadAnimals = new LinkedList<>();

   public SimulationEngine (AbstractWorldMap map, MapSettings mapSettings){
         this.map = map;
         this.mapSettings = mapSettings;
       for(int i = 0; i< mapSettings.startingAnimals; i++){
           Vector2d position = new Vector2d((int)(Math.random()*mapSettings.mapWidth), (int)(Math.random()*map.mapSettings.mapHeight));
              Animal animal = new Animal(position, map, MapDirection.NORTH, 0,
                      mapSettings, null);
                animalsList.add(animal);
                map.setStartingAnimal(animal);

       }
   }

    public void run(){
       int i = 0;
       while(i < 100){
              for(Animal animal : animalsList){
                animal.move();
              }
           LinkedList<Animal> newAnimals = map.simulateDayPass();
           animalsList.addAll(newAnimals);
           LinkedList<Animal> deadAnimals = new LinkedList<>();
           for(Animal animal : animalsList){
               if(animal.isDead()){
                   deadAnimals.add(animal);
                   map.removeDeadAnimal(animal);
                   deadAnimals.add(animal);
               }
           }
           animalsList.removeAll(deadAnimals);
           map.dailyGrassChange();
           i++;
       }
    }


}
