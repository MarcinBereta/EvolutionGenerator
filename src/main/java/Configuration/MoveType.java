package Configuration;

import mapElements.MapEffects;

public enum MoveType{
    FULLPREDESTINATION,
    BITOFMADDNESS;

    static MapEffects parse(int value){
        return switch (value){
            case 0 -> MapEffects.FULLPREDESTINATION;
            case 1 -> MapEffects.BITOFMADDNESS;
            default -> null;
        };
    }
}