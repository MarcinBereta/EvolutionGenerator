package mapManager;

import mapElements.*;

import java.io.InvalidObjectException;
import java.util.*;

public class IWorldMap implements IPositionChangeObserver{
    public int mapWidth;
    public int mapHeight;
    public int plantProfit;
    public int dayCost;
    public int startEnergy;
    public int copulationEnergy;
    public int jungleSize;
    public int edgeType;
    public int jungleType;
    public int moveOption;

    public int dailyGrass;

    public int randomGens;

    public int genSize;
    //Grass positions
    public Map<Vector2d, Grass> grass = new HashMap<>();
    //Animals positions
    public Map<Vector2d, ArrayList<Animal>> animals = new HashMap<>();
    //Array to check if location are possible for new gras
    //Empty jungle locations
    public LinkedList<Grass> junglePossible = new LinkedList<>();
    private Comparator<FieldHistory> dieCompare = Comparator.comparing(FieldHistory::getDeathCount);
    SortedSet<FieldHistory> dieSet = new TreeSet<>(dieCompare);

    public LinkedList<Animal> animalsList = new LinkedList<>();

    public LinkedList<Animal> animalHistoryList = new LinkedList<>();

    private Map<Vector2d, FieldHistory> fieldHistory = new HashMap<>();



    public IWorldMap(int mapWidth, int mapHeight, int plantProfit, int dayCost, int startEnergy, int copulationEnergy,
                     int jungleType, int edgeType, int startGrass,
                     int moveOption, int dailyGrass, int randomGens, int genSize) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.plantProfit = plantProfit;
        this.dayCost = dayCost;
        this.startEnergy = startEnergy;
        this.copulationEnergy = copulationEnergy;
        this.jungleType = jungleType;
        this.edgeType = edgeType;
        this.jungleSize = (int) ((int) mapHeight * mapWidth * 0.2);
        this.moveOption = moveOption;
        this.dailyGrass = dailyGrass;
        this.randomGens = randomGens;
        this.genSize = genSize;

        generateStartingJungle();

        for (int i = 0; i < startGrass; i++) {
            this.addGrass();
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
        animals.put(myAnimal.getPosition(), new ArrayList<>());
        animals.get(myAnimal.getPosition()).add(myAnimal);
        myAnimal.addObserver(this);
    }
    private void generateStartingJungle() {
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

    public void addGrass() {
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
        ArrayList<Animal> animals = this.animals.get(oldPosition);
        animals.remove(animal);
        ArrayList<Animal> newAnimals = this.animals.get(newPosition);
        if (newAnimals == null) {
            newAnimals = new ArrayList<>();
            this.animals.put(newPosition, newAnimals);
        }
        newAnimals.add(animal);
    }

//    public void removeDeadAnimals() throws InvalidObjectException {
//        if(animalsList.size() == 0){
//            throw new InvalidObjectException("No animals left");
//        }
//        List<Animal> AnimalsToRemove = new LinkedList<>();
//        for(Animal animal : animalsList){
//            if(animal.isDead()){
//                animals.get(animal.getPosition()).remove(animal);
//                animalHistoryList.add(animal);
//                dieSet.remove(fieldHistory.get(animal.getPosition()));
//                fieldHistory.get(animal.getPosition()).increaseDeathCount();
//                dieSet.add(fieldHistory.get(animal.getPosition()));
//                AnimalsToRemove.add(animal);
//            }
//        }
//        for(Animal animal : AnimalsToRemove){
//            animalsList.remove(animal);
//        }
//    }

//    public Vector2d correctPosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
//        if (newPosition.x >= mapWidth || newPosition.x <= 0) {
//            if (edgeType == 0) {
//                newPosition = new Vector2d(newPosition.x == 0? mapHeight -1: 0, newPosition.y);
//            } else {
//                newPosition = new Vector2d((int)Math.random() * mapWidth, (int)Math.random() * mapHeight);
//                animal.changeEnergy(-dayCost);
//            }
//        }
//        else if(newPosition.y >= mapHeight || newPosition.y <= 0) {
//            if (edgeType == 0) {
//                newPosition = oldPosition;
//                MapDirection newDirection = animal.getOrientation();
//                animal.updateDirection(newDirection== MapDirection.NORTH ? MapDirection.SOUTH : MapDirection.NORTH);
//            } else {
//                newPosition = new Vector2d((int)Math.random() * mapWidth, (int)Math.random() * mapHeight);
//                animal.changeEnergy(-dayCost);
//            }
//        }
//        return newPosition;
//    }


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
//    public void simulateDayPass() {
//        for(ArrayList<Animal> animalList: animals.values()){
//            if(animalList.size() > 0){
//                animalList.sort(animalComparator);
//                Animal strongestAnimal = animalList.get(0);
//                if(grass.containsKey(strongestAnimal.getPosition())){
//                    strongestAnimal.changeEnergy(plantProfit);
//                    if(jungleType == 1){
//                        FieldHistory field =  fieldHistory.remove(strongestAnimal.getPosition());
//                        dieSet.remove(field);
//                        field.increaseDeathCount();
//                        fieldHistory.put(strongestAnimal.getPosition(), field);
//                        dieSet.add(field);
//                        junglePossible.remove(new Grass(strongestAnimal.getPosition()));
//                        junglePossible.add(new Grass(dieSet.first().getPosition()));
//                    }
//
//                }
//                LinkedList <Animal> animalReproduction = new LinkedList<>();
//                for(Animal animal1: animalList){
//                    if(animal1.getEnergy() >= copulationEnergy){
//                        animalReproduction.add(animal1);
//                    }
//                }
//                while (animalReproduction.size() >= 2){
//                    Animal animal1 = animalReproduction.remove(0);
//                    Animal animal2 = animalReproduction.remove(0);
//                    Animal child = animal1.copulate(animal2);
//                    child.addObserver(this);
//                    animals.get(animal1.getPosition()).add(child);
//                }
//
//            }
//        }
//    }

//    public void dailyGrassChange(){
//        for(Grass myGrass: grass.values()){
//            Vector2d myGrassPos = myGrass.getPosition();
//            ArrayList<Animal> animalsAtPos = animals.get(myGrassPos);
//            if(animalsAtPos != null){
//                if(animalsAtPos.size() >= 1){
//                    grass.remove(myGrass);
//                }
//            }
//        }
//        for(int i = 0; i < dailyGrass; i++){
//            addGrass();
//        }
//    }

//    public void simulateMovement(){
//        for(Animal myAnimal: animalsList){
//            myAnimal.move();
//        }
//    }
//
//    public Vector2d getLowerLeft() {
//        return new Vector2d(0,0);}
//    public Vector2d getUpperRight() {
//        return new Vector2d(this.mapWidth,this.mapHeight);
//    }




}
