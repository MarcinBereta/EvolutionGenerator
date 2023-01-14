package Configuration.Types;

import mapElements.MapEffects;

public enum GenTypes {  // właściwie Gene
    FULLRANDOM,
    SMALLCORRECTION;

    public static MapEffects parse(int value) { // skoro mamy metodę int-> MapEffects, to po co w ogóle jest ten enum?
        return switch (value) {
            case 0 -> MapEffects.SMALLCORRECTION;
            case 1 -> MapEffects.FULLRANDOM;
            default -> null;    // wyjątek nie byłby lepszy?
        };
    }


}