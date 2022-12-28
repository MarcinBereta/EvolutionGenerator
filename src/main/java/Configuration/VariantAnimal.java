package Configuration;

public enum VariantAnimal{
    FULLPREDESTINATION,
    BITOFMADDNESS;

    static VariantAnimal parse(int value){
        return switch (value){
            case 0 -> VariantAnimal.FULLPREDESTINATION;
            case 1 -> VariantAnimal.BITOFMADDNESS;
            default -> null;
        };
    }
}