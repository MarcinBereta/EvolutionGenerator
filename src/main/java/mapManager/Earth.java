package mapManager;

import mapElements.Animal;
import mapElements.FieldHistory;
import mapElements.MapDirection;
import mapElements.Vector2d;

public class Earth extends AbstractWorldMap{
    public Earth(int mapWidth, int mapHeight, int plantProfit, int dayCost, int startEnergy, int copulationEnergy,
                     int jungleType,  int startGrass,
                     int moveOption, int dailyGrass, int randomGens, int genSize) {
        super(mapWidth, mapHeight, plantProfit, dayCost, startEnergy, copulationEnergy, jungleType, startGrass, moveOption, dailyGrass, randomGens, genSize);
    }
    @Override
    public Vector2d correctPosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        if (newPosition.x >= mapWidth || newPosition.x <= 0) {
            newPosition = new Vector2d(newPosition.x == 0? mapHeight -1: 0, newPosition.y);

        }
        else if(newPosition.y >= mapHeight || newPosition.y <= 0) {
            newPosition = oldPosition;
            MapDirection newDirection = animal.getOrientation();
            animal.updateDirection(newDirection== MapDirection.NORTH ? MapDirection.SOUTH : MapDirection.NORTH);
        }
        return newPosition;
    }

}
