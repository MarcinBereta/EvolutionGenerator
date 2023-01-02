package mapElements;

import mapManager.AbstractWorldMap;
import mapManager.MapSettings;

import java.util.ArrayList;

public class Animal implements IMapElement{
    public Vector2d position;
    public MapDirection orientation;
    private int energy =0;
    private int age =0;

    private int childrenCount = 0;
    private MapSettings mapSettings;
    private ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    public Gens gens;
    private AbstractWorldMap map;

    public Animal(Vector2d position, AbstractWorldMap map, MapDirection orientation,
                  int energy, MapSettings mapSettings, Gens gens) {
        this.mapSettings = mapSettings;
        this.energy = energy + this.mapSettings.startEnergy;
        this.map = map;
        this.position = position;
        this.orientation = orientation;
        if(gens == null){
            this.gens = new Gens(mapSettings);
            this.gens.generateStartingGens();
        }
        else{
            this.gens = gens;
        }

    }

    public boolean isDead(){
        return this.energy <= 0;
    }

    public void changeEnergy(int energy){
        this.energy += energy;
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
        int childEnergy = this.mapSettings.copulationEnergy*2;

        this.incrementChildCount();
        partner.incrementChildCount();
        Gens childGens = new Gens(this.mapSettings);
        childGens.generateGens(this.gens, partner.gens, this.energy + mapSettings.copulationEnergy, partner.getEnergy()+ mapSettings.copulationEnergy);
        this.changeEnergy(-this.mapSettings.copulationEnergy);
        partner.changeEnergy(-this.mapSettings.copulationEnergy);
        int childOrientationIndex =(int) Math.random()*8;
        MapDirection childOrientation = MapDirection.NORTH;
        for(int i =0 ; i< childOrientationIndex; i++){
            childOrientation = childOrientation.next();
        }

        return new Animal(this.getPosition(),this.map, childOrientation,childEnergy,
                this.mapSettings,childGens);
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

}
