package mapManager;

import mapElements.Animal;
import mapElements.MapDirection;
import mapElements.Vector2d;

public class Earth extends AbstractWorldMap{

    public Earth(MapSettings mapSettings) {

        super(mapSettings);
    }

    @Override
    public Vector2d correctPosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        if (newPosition.x >= super.mapSettings.mapWidth || newPosition.x <= 0) {
            newPosition = new Vector2d(newPosition.x == 0? super.mapSettings.mapHeight -1: 0, newPosition.y);

        }
        else if(newPosition.y >= super.mapSettings.mapHeight || newPosition.y <= 0) {
            newPosition = oldPosition;
            MapDirection newDirection = animal.getOrientation();
            animal.updateDirection(newDirection== MapDirection.NORTH ? MapDirection.SOUTH : MapDirection.NORTH);
        }
        return newPosition;
    }

}
