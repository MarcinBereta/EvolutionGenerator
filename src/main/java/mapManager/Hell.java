package mapManager;

import mapElements.Animal;
import mapElements.MapDirection;
import mapElements.Vector2d;

public class Hell extends AbstractWorldMap{
    public Hell(MapSettings mapSettings) {
        super(mapSettings);
    }
    @Override
    public Vector2d correctPosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        if (newPosition.x >= super.mapSettings.mapWidth || newPosition.x <= 0) {
                newPosition = new Vector2d((int)Math.random() * super.mapSettings.mapWidth, (int)Math.random() * super.mapSettings.mapHeight);
                animal.changeEnergy(-super.mapSettings.dayCost);
        }
        else if(newPosition.y >= super.mapSettings.mapHeight || newPosition.y <= 0) {
                newPosition = new Vector2d((int)Math.random() * super.mapSettings.mapWidth, (int)Math.random() * super.mapSettings.mapHeight);
                animal.changeEnergy(-super.mapSettings.dayCost);
        }
        return newPosition;
    }
}
