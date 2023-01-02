package mapManager;

import mapElements.Animal;
import mapElements.Vector2d;

import java.util.LinkedList;

public interface AbstractMapInterface {

    void setStartingAnimal(Animal animal);
    void generateStartingJungle();

    void addGrass();
    void addGrassToJungle();
    void addGrassToEdge();
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal o);
    void removeDeadAnimal(Animal animal);
    Vector2d correctPosition(Vector2d oldPosition, Vector2d newPosition, Animal animal);
    LinkedList<Animal> simulateDayPass();
    void dailyGrassChange();
//    void getVisualisation();
    int getTotalGrassAmount();
    LinkedList<Animal> getAnimalsAtPosition(Vector2d position);
}