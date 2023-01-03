package Configuration.Types;

import mapElements.MapEffects;

public enum GenTypes {
    FULLRANDOM,
    SMALLCORRECTION;

    public static MapEffects parse(int value){
        return switch (value){
            case 0 -> MapEffects.SMALLCORRECTION;
            case 1 -> MapEffects.FULLRANDOM;
            default -> null;
        };
    }


}