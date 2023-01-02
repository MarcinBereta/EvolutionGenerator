package Configuration;

import mapElements.MapEffects;

public enum VariantMutation{
    FULLRANDOM,
    SMALLCORRECTION;

    static MapEffects parse(int value){
        return switch (value){
            case 0 -> MapEffects.SMALLCORRECTION;
            case 1 -> MapEffects.FULLRANDOM;
            default -> null;
        };
    }


}