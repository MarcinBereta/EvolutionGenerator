package Configuration;

public enum VariantGrass{
    TOXIC,
    EQUATOR;

    static VariantGrass parse(int value){
        return switch (value){
            case 0 -> VariantGrass.TOXIC;
            case 1 -> VariantGrass.EQUATOR;
            default -> null;
        };
    }
}