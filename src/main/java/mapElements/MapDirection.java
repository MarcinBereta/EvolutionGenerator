package mapElements;

public enum MapDirection {
    NORTH,
    NORTHWEST,
    WEST,
    SOUTHWEST,
    SOUTH,
    SOUTHEAST,
    EAST,
    NORTHEAST;

    public String toString(){
        switch(this) {
            case NORTH: return "Polnoc";
            case SOUTH: return "Poludnie";
            case EAST: return "WSCHOD";
            case WEST: return "ZACHOD";
            case NORTHWEST: return "Polnocny Zachod";
            case SOUTHEAST: return "Poludniowy Wschod";
            case SOUTHWEST: return "Poludniowy Zachod";
            case NORTHEAST: return "Polnocny Wschod";
            default: return null;
        }
    }

    public MapDirection next(){
        switch(this) {
            case NORTH: return NORTHEAST;
            case SOUTH: return SOUTHWEST;
            case EAST: return SOUTHEAST;
            case WEST: return NORTHWEST;
            case NORTHWEST: return WEST;
            case SOUTHEAST: return SOUTH;
            case SOUTHWEST: return SOUTH;
            case NORTHEAST: return EAST;
            default: return null;
        }
    }
    public MapDirection previous(){
        switch(this) {
            case NORTH: return NORTHWEST;
            case SOUTH: return SOUTHEAST;
            case EAST: return NORTHEAST;
            case WEST: return SOUTHWEST;
            case NORTHWEST: return NORTH;
            case SOUTHEAST: return EAST;
            case SOUTHWEST: return SOUTH;
            case NORTHEAST: return NORTH;
            default: return null;
        }
    }
    public Vector2d toUnitVector(){
        switch(this) {
            case NORTH: return new Vector2d(0, 1);
            case SOUTH: return new Vector2d(0, -1);
            case EAST: return new Vector2d(1, 0);
            case WEST: return new Vector2d(-1, 0);
            case NORTHWEST: return new Vector2d(-1, 1);
            case SOUTHEAST: return new Vector2d(1, -1);
            case SOUTHWEST: return new Vector2d(-1, -1);
            case NORTHEAST: return new Vector2d(1, 1);
            default: return null;
        }
    }

}
