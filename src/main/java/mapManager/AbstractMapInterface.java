package mapManager;

import mapElements.Animal;
import mapElements.Vector2d;
import mapElements.IPositionChangeObserver;

import java.util.LinkedList;

public interface AbstractMapInterface extends IPositionChangeObserver {  // czemu nie IMap?

    void setStartingAnimal(Animal animal);  // ??

    void generateStartingJungle();

    void addGrass();

    void addGrassToJungle();

    void addGrassToEdge();

    void removeDeadAnimal(Animal animal);

    Vector2d correctPosition(Vector2d oldPosition, Vector2d newPosition, Animal animal);

    LinkedList<Animal> simulateDayPass();

    void dailyGrassChange();

    //    void getVisualisation();
    int getTotalGrassAmount();

    LinkedList<Animal> getAnimalsAtPosition(Vector2d position);
}