package mapElements;

import mapManager.IWorldMap;

import java.util.ArrayList;

public class Animal implements IMapElement{
    public Vector2d position;
    public MapDirection orientation;
    private int energy =0;
    private int startEnergy;
    private int age =0;
    private int childrenCount = 0;
    private int copulationEnergy;
    private ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    public Gens gens;
    private int genSize;
    private int randomGens;
    private int genStep;
    private IWorldMap map;
    private int plantsEaten = 0;

    public Animal(Vector2d position, IWorldMap map, MapDirection orientation,
                  int energy, int startEnergy, int randomGens, int genSize,
                  int genStep, int copulationEnergy, Gens gens ){
        this.energy = energy+ startEnergy;
        this.map = map;
        this.position = position;
        this.orientation = MapDirection.NORTH;
        this.copulationEnergy = copulationEnergy;
        this.startEnergy = startEnergy;
        this.orientation = orientation;
        this.genSize = genSize;
        this.randomGens = randomGens;
        this.genStep = genStep;
        if(gens == null){
            this.gens = new Gens(genSize,randomGens,genStep);
            this.gens.generateStartingGens();
        }
        else{
            this.gens = gens;
        }

    }

    public boolean isDead(){
        return this.energy <= 0;
    }

    public void increaseGrassCount(){
        this.plantsEaten++;
    }

    public void changeEnergy(int energy){
        this.energy += energy;
        if(this.energy < 0){
            this.energy = 0;
        }
    }

    public void move(){
        int move = this.gens.getGen();
        for(int i = 0; i < move; i++){
            this.orientation = this.orientation.next();
        }
        Vector2d oldPosition = this.position;
        this.position = this.position.add(this.orientation.toUnitVector());
        this.position = this.map.correctPosition(oldPosition, this.position, this);
        positionChanged(oldPosition, this.position);
        this.age++;
    }

    public void updateDirection(MapDirection direction){
        this.orientation = direction;
    }


    public Animal copulate(Animal partner){
        int childEnergy = this.copulationEnergy*2;

        this.incrementChildCount();
        partner.incrementChildCount();
        Gens childGens = new Gens(genSize,randomGens,genStep);
        childGens.generateGens(this.gens, partner.gens, this.energy, partner.getEnergy());
        this.changeEnergy(-copulationEnergy);
        partner.changeEnergy(-copulationEnergy);
        int childOrientationIndex =(int) Math.random()*8;
        MapDirection childOrientation = MapDirection.NORTH;
        for(int i =0 ; i< childOrientationIndex; i++){
            childOrientation = childOrientation.next();
        }
        return new Animal(this.getPosition(),this.map, childOrientation,childEnergy, this.startEnergy, this.randomGens, this.genSize,this.genStep,this.copulationEnergy,childGens);
    }


    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }
    private void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer : observers){
            observer.positionChanged(oldPosition, newPosition, this);
        }
    }
    public int getEnergy(){
        return this.energy;
    }

    public int getAge(){
        return this.age;
    }

    public int getPlantsCount(){
        return this.plantsEaten;
    }
    public void incrementChildCount (){
        this.childrenCount ++;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    public int getChildrenCount(){
        return this.childrenCount;
    }
    public MapDirection getOrientation(){
        return this.orientation;
    }
    public String toString(){
        return this.position.toString() + " " + this.orientation.toString()+" " + this.gens.toString();
    }
}
