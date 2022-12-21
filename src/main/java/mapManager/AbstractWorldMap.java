package mapManager;

import mapElements.*;

import java.io.InvalidObjectException;
import java.util.*;

public abstract class AbstractWorldMap implements IPositionChangeObserver {
    protected int mapWidth;
    protected int mapHeight;
    protected int plantProfit;
    protected int dayCost;
    protected int startEnergy;
    protected int copulationEnergy;
    protected int jungleSize;
    protected int jungleType;
    protected int moveOption;

    protected int dailyGrass;

    protected int randomGens;

    protected int genSize;
    //Grass positions
    protected Map<Vector2d, Grass> grass = new HashMap<>();
    //Animals positions
    protected Map<Vector2d, LinkedList<Animal>> animals = new HashMap<>();
    //Array to check if location are possible for new gras
    //Empty jungle locations
    protected LinkedList<Grass> junglePossible = new LinkedList<>();
    protected Comparator<FieldHistory> dieCompare = Comparator.comparing(FieldHistory::getDeathCount);
    protected SortedSet<FieldHistory> dieSet = new TreeSet<>(dieCompare);

    protected LinkedList<Animal> animalsList = new LinkedList<>();

    protected LinkedList<Animal> animalHistoryList = new LinkedList<>();

    protected Map<Vector2d, FieldHistory> fieldHistory = new HashMap<>();


    public AbstractWorldMap(int mapWidth, int mapHeight, int plantProfit, int dayCost, int startEnergy, int copulationEnergy,
                 int jungleType, int startGrass,
                 int moveOption, int dailyGrass, int randomGens, int genSize) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.plantProfit = plantProfit;
        this.dayCost = dayCost;
        this.startEnergy = startEnergy;
        this.copulationEnergy = copulationEnergy;
        this.jungleType = jungleType;
        this.jungleSize = (int) ((int) mapHeight * mapWidth * 0.2);
        this.moveOption = moveOption;
        this.dailyGrass = dailyGrass;
        this.randomGens = randomGens;
        this.genSize = genSize;

        generateStartingJungle();

        for (int i = 0; i < startGrass; i++) {
            addGrass();
        }
        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j < mapHeight; j++){
                dieSet.add(new FieldHistory(new Vector2d(i,j), 0));
                fieldHistory.put(new Vector2d(i,j), new FieldHistory(new Vector2d(i,j), 0));
            }
        }
    }


    public void setStartingAnimal(Animal myAnimal){
        animalsList.add(myAnimal);
        animals.put(myAnimal.getPosition(), new LinkedList<>());
        animals.get(myAnimal.getPosition()).add(myAnimal);
        myAnimal.addObserver((IPositionChangeObserver) this);
    }
    protected void generateStartingJungle() {
        if (jungleType == 0) {
            int tempJunglex = 0;
            int jungleStartY = (mapHeight) / 2;
            int tempJungley = (mapHeight) / 2;
            int rowCount = 0;
            for (int i = 0; i < this.jungleSize; i++) {
                junglePossible.add(new Grass(new Vector2d(tempJunglex, tempJungley)));
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
                if(grass.containsKey(new Vector2d(x,y))){
                    continue;
                }else{
                    junglePossible.add(new Grass(new Vector2d(x,y)));
                    elementCount++;
                }
            }
        }
    }

    protected void addGrass() {
        int probability = (int) (Math.random() * 100);
        if (probability < 80) {
            this.addGrassToJungle();
        } else {
            this.addGrassToEdge();
        }
    }

    protected void addGrassToJungle() {
        int index = (int) (Math.random() * junglePossible.size());
        Grass grass = junglePossible.get(index);
        junglePossible.remove(index);
        this.grass.put(grass.getPosition(), grass);
    }

    protected void addGrassToEdge() {
        LinkedList<Vector2d> edgePossible = new LinkedList<>();
        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j < mapHeight; j++){
                if(grass.containsKey(new Vector2d(i,j)) || junglePossible.contains(new Grass(new Vector2d(i,j)))){
                    continue;
                }else{
                    edgePossible.add(new Vector2d(i,j));
                }
            }
        }
        if (edgePossible.size() == 0) {
            return;
        }
        int index = (int) (Math.random() * edgePossible.size());
        Grass myGrass = new Grass(edgePossible.get(index));
        grass.put(myGrass.getPosition(), myGrass);
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

    public void removeDeadAnimals() throws InvalidObjectException {
        if(animalsList.size() == 0){
            throw new InvalidObjectException("No animals left");
        }
        List<Animal> AnimalsToRemove = new LinkedList<>();
        for(Animal animal : animalsList){
            if(animal.isDead()){
                animals.get(animal.getPosition()).remove(animal);
                animalHistoryList.add(animal);
                dieSet.remove(fieldHistory.get(animal.getPosition()));
                fieldHistory.get(animal.getPosition()).increaseDeathCount();
                dieSet.add(fieldHistory.get(animal.getPosition()));
                AnimalsToRemove.add(animal);
            }
        }
        for(Animal animal : AnimalsToRemove){
            animalsList.remove(animal);
        }
    }

    public Vector2d correctPosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        return newPosition;
    }


    public static Comparator<Animal> animalComparator = new Comparator<Animal>() {
        @Override
        public int compare(Animal o1, Animal o2) {
            if (o1.getEnergy() > o2.getEnergy()) {
                return -1;
            } else if (o1.getEnergy() < o2.getEnergy()) {
                return 1;
            } else {
                if (o1.getAge() > o2.getAge()) {
                    return -1;
                } else if (o1.getAge() < o2.getAge()) {
                    return 1;
                } else {
                    if (o1.getChildrenCount() > o2.getChildrenCount()) {
                        return -1;
                    } else if (o1.getChildrenCount() < o2.getChildrenCount()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
        }
    };
    public void simulateDayPass() {
        for(LinkedList<Animal> animalList: animals.values()){
            if(animalList.size() > 0){
                animalList.sort(animalComparator);
                Animal strongestAnimal = animalList.get(0);
                if(grass.containsKey(strongestAnimal.getPosition())){
                    strongestAnimal.changeEnergy(plantProfit);
                    if(jungleType == 1){
                        FieldHistory field =  fieldHistory.remove(strongestAnimal.getPosition());
                        dieSet.remove(field);
                        field.increaseDeathCount();
                        fieldHistory.put(strongestAnimal.getPosition(), field);
                        dieSet.add(field);
                        junglePossible.remove(new Grass(strongestAnimal.getPosition()));
                        junglePossible.add(new Grass(dieSet.first().getPosition()));
                    }

                }
                LinkedList <Animal> animalReproduction = new LinkedList<>();
                for(Animal animal1: animalList){
                    if(animal1.getEnergy() >= copulationEnergy){
                        animalReproduction.add(animal1);
                    }
                }
                while (animalReproduction.size() >= 2){
                    Animal animal1 = animalReproduction.remove(0);
                    Animal animal2 = animalReproduction.remove(0);
                    Animal child = animal1.copulate(animal2);
                    child.addObserver((IPositionChangeObserver) this);
                    animals.get(animal1.getPosition()).add(child);
                }

            }
        }
    }

    public void dailyGrassChange(){
        for(Grass myGrass: grass.values()){
            Vector2d myGrassPos = myGrass.getPosition();
            LinkedList<Animal> animalsAtPos = animals.get(myGrassPos);
            if(animalsAtPos != null){
                if(animalsAtPos.size() >= 1){
                    grass.remove(myGrass);
                }
            }
        }
        for(int i = 0; i < dailyGrass; i++){
            addGrass();
        }
    }

    public void simulateMovement(){
        for(Animal myAnimal: animalsList){
            myAnimal.move();
        }
    }
}