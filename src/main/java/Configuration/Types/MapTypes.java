package Configuration.Types;


public enum MapTypes {
    EARTH,
    HELL;

    public static MapTypes parse(int value){
        return switch (value){
            case 0 -> MapTypes.EARTH;
            case 1 -> MapTypes.HELL;
            default -> null;
        };
    }
}
