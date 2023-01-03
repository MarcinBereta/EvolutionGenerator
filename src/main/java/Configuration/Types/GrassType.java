package Configuration.Types;

import mapElements.MapEffects;

public enum GrassType{
    TOXIC,
    EQUATOR;

    public static MapEffects parse(int value){
        return switch (value){
            case 0 -> MapEffects.TOXIC;
            case 1 -> MapEffects.EQUATOR;
            default -> null;
        };
    }
}