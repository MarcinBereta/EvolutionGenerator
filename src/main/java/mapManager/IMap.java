package mapManager;

import Configuration.MapConstants;
import mapElements.Animal;
import mapElements.IGrass;
import mapElements.IMapElement;
import mapElements.Vector2d;

public interface IMap {

    void addAnimal(Animal animal, Vector2d position);

    void removeAnimal(Animal animal);
    void feedAnimals(IGrass plants);

    Vector2d changePosition(Animal animal, Vector2d newPosition);

    boolean canMoveTo(Vector2d position);
    int getHeight();
    int getWidth();

    IMapElement objectAt (Vector2d position);

    Vector2d getLowerLeft();
    Vector2d getUpperRight();

    MapConstants getMapConstants();
}