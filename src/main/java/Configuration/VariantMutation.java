package Configuration;

public enum VariantMutation{
    FULLRANDOM,
    SMALLCORRECTION;

    static VariantMutation parse(int value){
        return switch (value){
            case 0 -> VariantMutation.SMALLCORRECTION;
            case 1 -> VariantMutation.FULLRANDOM;
            default -> null;
        };
    }


}