package mapManager;

import mapElements.Animal;
import mapElements.Vector2d;

public class Hell extends AbstractWorldMap{
    public Hell(MapSettings mapSettings) {
        super(mapSettings);
    }
    @Override
    public Vector2d correctPosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        if (newPosition.x > super.mapSettings.mapWidth || newPosition.x < 0) {
                animal.changeEnergy(-super.mapSettings.dayCost);
                Vector2d newVect =  new Vector2d((int)(Math.random() * super.mapSettings.mapWidth), (int)(Math.random() * super.mapSettings.mapHeight));
                return newVect;
        }
        else if(newPosition.y > super.mapSettings.mapHeight || newPosition.y < 0) {
                animal.changeEnergy(-super.mapSettings.dayCost);
                Vector2d newVect =  new Vector2d((int)(Math.random() * super.mapSettings.mapWidth), (int)(Math.random() * super.mapSettings.mapHeight));
                return newVect;
        }
        return newPosition;
    }
}
