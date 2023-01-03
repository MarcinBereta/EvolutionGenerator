package mapManager;

import javafx.scene.shape.Circle;
import mapElements.*;

import java.util.*;

public abstract class AbstractWorldMap implements IPositionChangeObserver, AbstractMapInterface{
    public MapSettings mapSettings;
    private int jungleSize;
    //Grass positions
    protected Map<Vector2d, Grass> grass = new HashMap<>();
    //Animals positions
    protected Map<Vector2d, LinkedList<Animal>> animals = new HashMap<>();
    //Array to check if location are possible for new gras
    //Empty jungle locations
    protected LinkedList<Grass> junglePossible = new LinkedList<>();
    protected Comparator<FieldHistory> dieCompare = Comparator.comparing(FieldHistory::getDeathCount);
    protected SortedSet<FieldHistory> dieSet = new TreeSet<>(dieCompare);

    protected Map<Vector2d, FieldHistory> fieldHistory = new HashMap<>();



    public AbstractWorldMap(MapSettings settings) {
        this.mapSettings= settings;
        this.jungleSize = (int) ((int) this.mapSettings.mapHeight * this.mapSettings.mapWidth * 0.2);

        generateStartingJungle();

        for (int i = 0; i < this.mapSettings.startGrass; i++) {
            addGrass();
        }
        for(int i = 0; i < this.mapSettings.mapWidth; i++){
            for(int j = 0; j < this.mapSettings.mapHeight; j++){
                dieSet.add(new FieldHistory(new Vector2d(i,j), 0));
                fieldHistory.put(new Vector2d(i,j), new FieldHistory(new Vector2d(i,j), 0));
            }
        }
    }



    public void setStartingAnimal(Animal myAnimal){
        animals.put(myAnimal.getPosition(), new LinkedList<>());
        animals.get(myAnimal.getPosition()).add(myAnimal);
        myAnimal.addObserver(this);
    }
    public void generateStartingJungle() {
        if (this.mapSettings.jungleType == MapEffects.EQUATOR) {
            int tempJunglex = 0;
            int jungleStartY = (this.mapSettings.mapHeight) / 2;
            int tempJungley = (this.mapSettings.mapHeight) / 2;
            int rowCount = 0;
            for (int i = 0; i < this.jungleSize; i++) {
                junglePossible.add(new Grass(new Vector2d(tempJunglex, tempJungley)));
                tempJunglex = (tempJunglex + 1) % (this.mapSettings.mapWidth);
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
        } else if(this.mapSettings.jungleType == MapEffects.TOXIC){
            int elementCount = 0;
            while (elementCount < jungleSize) {
                int x = (int) (Math.random() * this.mapSettings.mapWidth);
                int y = (int) (Math.random() * this.mapSettings.mapHeight);
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

    public void addGrassToJungle() {
        if(junglePossible.size() == 0){
            return;
        }
        int index = (int) (Math.random() * junglePossible.size());
        Grass grass = junglePossible.get(index);
        junglePossible.remove(index);
        this.grass.put(grass.getPosition(), grass);
    }

    public void addGrassToEdge() {
        LinkedList<Vector2d> edgePossible = new LinkedList<>();
        for(int i = 0; i < this.mapSettings.mapWidth; i++){
            for(int j = 0; j < this.mapSettings.mapHeight; j++){
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

    public void removeDeadAnimal(Animal animal){
        this.animals.get(animal.getPosition()).remove(animal);
    }
    public Vector2d correctPosition(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        return newPosition;
    }

    private static Comparator<Animal> animalComparator = new Comparator<Animal>() {
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
    public LinkedList<Animal> simulateDayPass() {
        LinkedList<Animal> newAnimals = new LinkedList<>();
        for(LinkedList<Animal> animalList: animals.values()){;
            if(animalList.size() > 0){
                animalList.sort(animalComparator);
                Animal strongestAnimal = animalList.get(0);
                if(grass.containsKey(strongestAnimal.getPosition())){
                    strongestAnimal.changeEnergy(this.mapSettings.plantProfit);
                    if(this.mapSettings.jungleType == MapEffects.TOXIC){
                        FieldHistory field =  fieldHistory.remove(strongestAnimal.getPosition());
                        dieSet.remove(field);
                        field.increaseDeathCount();
                        fieldHistory.put(strongestAnimal.getPosition(), field);
                        dieSet.add(field);
                        junglePossible.remove(new Grass(strongestAnimal.getPosition()));
                        junglePossible.add(new Grass(dieSet.first().getPosition()));
                    }
                }

                for(Animal animal: animalList){
                    animal.changeEnergy(-this.mapSettings.dayCost);
                }

                LinkedList <Animal> animalReproduction = new LinkedList<>();
                for(Animal animal1: animalList){
                    if(animal1.getEnergy() >= this.mapSettings.requiredCopulationEnergy){
                        animalReproduction.add(animal1);
                    }
                }
                if(animalReproduction.size() >= 2){
                    System.out.println(animalReproduction.size() + "pos: " + animalReproduction.get(0).getPosition());
                }
                while (animalReproduction.size() >= 2){
                    Animal animal1 = animalReproduction.remove(0);
                    Animal animal2 = animalReproduction.remove(0);
                    Animal child = animal1.copulate(animal2);
                    child.addObserver(this);
                    animalList.add(child);
                    newAnimals.add(child);
                }

            }
        }
        return newAnimals;
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
        for(int i = 0; i < this.mapSettings.dailyGrass; i++){
            addGrass();
        }
    }

    public String[] getVisualisation(Vector2d position, Map<Circle, Vector2d> positions, Circle circle){
        String myColor = "white";
        String type = "none";
        if(animals.get(position) == null || animals.get(position).size() == 0){
            type = "none";
            myColor = "transparent";
        }else if(animals.get(position).size() > 0 && animals.get(position).size() < 6) {
            type = " " + position;
            myColor = "blue";
        }else if (animals.get(position).size() >= 6){
            type = " " + position;
            myColor = "red";
        }
        if(grass.get(position) != null && type.equals("none")){
            type = "grass";
            myColor = "green";
        }
        positions.put(circle, position);
        String[] visualisation= {myColor, type};
        return visualisation;
    }

    public int getTotalGrassAmount() {
        return grass.size();
    }

    public LinkedList<Animal> getAnimalsAtPosition(Vector2d position){
        if(animals.get(position) != null){
            return animals.get(position);
        }
        return new LinkedList<Animal>();
    }

    public int getNumberOfEmptyFields() {
        boolean[][] fields = new boolean[mapSettings.mapWidth+1][mapSettings.mapHeight+1];
        int emptyFields = 0;

        for (Vector2d position : grass.keySet()) {
            if (position.x<=mapSettings.mapWidth && position.y<=mapSettings.mapHeight && position.x>=0 && position.y>=0){
            fields[position.x][position.y] = true;
        }}
        for (Vector2d position : animals.keySet()) {
            if (position.x<=mapSettings.mapWidth && position.y<=mapSettings.mapHeight && position.x>=0 && position.y>=0){
            fields[position.x][position.y] = true;
        }}

        for (int i = 1; i < mapSettings.mapWidth; i++) {
            for (int j = 1; j < mapSettings.mapHeight; j++) {
                if (!fields[i][j]) {
                    emptyFields++;
                }
            }
        }

        return emptyFields;
    }

}
