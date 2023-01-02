package Configuration;

import mapElements.MapEffects;

public enum VariantGrass{
    TOXIC,
    EQUATOR;

    static MapEffects parse(int value){
        return switch (value){
            case 0 -> MapEffects.TOXIC;
            case 1 -> MapEffects.EQUATOR;
            default -> null;
        };
    }
}