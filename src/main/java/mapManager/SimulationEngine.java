package mapManager;

import mapElements.Animal;
import mapElements.MapDirection;
import mapElements.Vector2d;

import java.io.InvalidObjectException;
import java.util.List;
import java.util.Vector;

public class SimulationEngine {
    IWorldMap map;
   public SimulationEngine (IWorldMap map, int startAnimals){
         this.map = map;
       for(int i = 0; i< startAnimals; i++){
           Vector2d position = new Vector2d((int)(Math.random()*map.mapWidth), (int)(Math.random()*map.mapHeight));
              Animal animal = new Animal(position, map, MapDirection.NORTH, 0,
                      map.startEnergy, map.randomGens, map.genSize, map.randomGens, map.copulationEnergy, null);
                map.setStartingAnimal(animal);

       }
   }

    public void run(){
       int i = 0;
       while(i < 100){
           map.simulateMovement();
           try{
               map.removeDeadAnimals();
           }catch(InvalidObjectException e){
               System.out.println("Invalid object");
               break;
           }
           map.simulateDayPass();
           map.dailyGrassChange();
           i++;
       }
    }


}
