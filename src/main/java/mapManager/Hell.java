package mapManager;

import mapElements.Animal;
import mapElements.MapDirection;
import mapElements.Vector2d;

public class Hell extends AbstractWorldMap{
    public Hell(int mapWidth, int mapHeight, int plantProfit, int dayCost, int startEnergy, int copulationEnergy,
                 int jungleType,  int startGrass,
                 int moveOption, int dailyGrass, int randomGens, int genSize) {
        super(mapWidth, mapHeight, plantProfit, dayCost, startEnergy, copulationEnergy, jungleType, startGrass, moveOption, dailyGrass, randomGens, genSize);
    }
    @Override
    public Vector2d correctPosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        if (newPosition.x >= mapWidth || newPosition.x <= 0) {
                newPosition = new Vector2d((int)Math.random() * mapWidth, (int)Math.random() * mapHeight);
                animal.changeEnergy(-dayCost);
        }
        else if(newPosition.y >= mapHeight || newPosition.y <= 0) {
                newPosition = new Vector2d((int)Math.random() * mapWidth, (int)Math.random() * mapHeight);
                animal.changeEnergy(-dayCost);
        }
        return newPosition;
    }
}
