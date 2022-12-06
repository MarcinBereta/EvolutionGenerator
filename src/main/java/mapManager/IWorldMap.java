package mapManager;

import mapElements.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class IWorldMap {
    private int mapWidth;
    private int mapHeight;
    private int jungleHeight;
    private int jungleWidth;

    private int plantProfit;
    private int dayCost;
    private int startEnergy;
    private int copulationEnergy;
    private Vector2d jungleCords;
    private int jungleSize;
    private int edgeType;
    int jungleType;
    int moveOption;

    //Grass positions
    public Map<Vector2d, Grass> grass = new HashMap<>();
    //Animals positions
    public Map<Vector2d, LinkedList<Animal>> animals = new HashMap<>();
    //Array to check if location are possible for new gras
    public int[][] positionAt;
    //Empty jungle locations
    public LinkedList<Grass> junglePossible = new LinkedList<>();
    private int[][] dieHistory;

    public IWorldMap(int mapWidth, int mapHeight, int plantProfit, int dayCost, int startEnergy, int copulationEnergy,
                     int jungleType, int edgeType, int startGrass, int moveOption) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.plantProfit = plantProfit;
        this.dayCost = dayCost;
        this.startEnergy = startEnergy;
        this.copulationEnergy = copulationEnergy;
        this.dieHistory = new int[mapWidth][mapHeight];
        this.jungleType = jungleType;
        this.edgeType = edgeType;
        this.jungleSize = (int) ((int) mapHeight * mapWidth * 0.2);
        this.positionAt = new int[mapWidth][mapHeight];
        this.moveOption = moveOption;
        generateStartingJungle();

        for (int i = 0; i < startGrass; i++) {
            this.addGrass();
        }
    }

    private void generateStartingJungle() {
        if (jungleType == 0) {
            int tempJunglex = 0;
            int jungleStartY = (mapHeight) / 2;
            int tempJungley = (mapHeight) / 2;
            int rowCount = 0;
            for (int i = 0; i < this.jungleSize; i++) {
                junglePossible.add(new Grass(new Vector2d(tempJunglex, tempJungley)));
                positionAt[tempJunglex][tempJungley] = 1;
                tempJunglex = (tempJunglex + 1) % (mapWidth);
                if (tempJunglex == 0) {
                    if (rowCount == 0) {
                        tempJungley++;
                        rowCount++;
                    } else if (rowCount > jungleStartY) {
                        rowCount++;
                        tempJungley = tempJungley - rowCount;
                    } else {
                        rowCount++;
                        tempJungley = tempJungley + rowCount;
                    }
                }
            }
        } else {
            int elementCount = 0;
            while (elementCount < jungleSize) {
                int x = (int) (Math.random() * mapWidth);
                int y = (int) (Math.random() * mapHeight);
                if (positionAt[x][y] == 0) {
                    junglePossible.add(new Grass(new Vector2d(x, y)));
                    positionAt[x][y] = 1;
                    elementCount++;
                }
            }
        }
    }

    private void addGrass() {
        int probability = (int) (Math.random() * 100);
        if (probability < 80) {
            this.addGrassToJungle();
        } else {
            this.addGrassToEdge();
        }
    }

    private void addGrassToJungle() {
        int index = (int) (Math.random() * junglePossible.size());
        Grass grass = junglePossible.get(index);
        junglePossible.remove(index);
        this.grass.put(grass.getPosition(), grass);
    }

    private void addGrassToEdge() {
        boolean positionPossible = false;
        while (!positionPossible) {
            int x = (int) (Math.random() * mapWidth);
            int y = (int) (Math.random() * mapHeight);
            Vector2d position = new Vector2d(x, y);
            if (positionAt[x][y] == 0) {
                positionPossible = true;
                this.grass.put(position, new Grass(position));
                positionAt[x][y] = 1;
            }
        }
    }

    public boolean grassAt(Vector2d position) {
        return this.grass.get(position) != null;
    }

    public boolean animalAt(Vector2d position) {
        return this.animals.get(position) != null;
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        LinkedList<Animal> animals = this.animals.get(oldPosition);
        animals.remove(animal);
        LinkedList<Animal> newAnimals = this.animals.get(newPosition);
        if (newAnimals == null) {
            newAnimals = new LinkedList<>();
            this.animals.put(newPosition, newAnimals);
        }
        newAnimals.add(animal);
    }

    public void removeDeadAnimal(Animal animal) {
        LinkedList<Animal> animals = this.animals.get(animal.getPosition());
        animals.remove(animal);
        if (animals.size() == 0) {
            this.animals.remove(animal.getPosition());
        }
    }

    public Vector2d correctPosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        if (newPosition.x >= mapWidth || newPosition.x <= 0) {
            if (edgeType == 0) {
                newPosition = new Vector2d(newPosition.x == 0? mapHeight -1: 0, newPosition.y);
            } else {
                newPosition = new Vector2d((int)Math.random() * mapWidth, (int)Math.random() * mapHeight);
                animal.changeEnergy(-dayCost);
            }
        }
        else if(newPosition.y >= mapHeight || newPosition.y <= 0) {
            if (edgeType == 0) {
                newPosition = oldPosition;
                MapDirection newDirection = animal.getOrientation();
                animal.updateDirection(newDirection== MapDirection.NORTH ? MapDirection.SOUTH : MapDirection.NORTH);
            } else {
                newPosition = new Vector2d((int)Math.random() * mapWidth, (int)Math.random() * mapHeight);
                animal.changeEnergy(-dayCost);
            }
        }
        return newPosition;
    }

    public void simulateDay(Animal animal) {
        animal.changeEnergy(-dayCost);
        if(animal.isDead()){
            this.removeDeadAnimal(animal);
        }
    }
}
