package Configuration;


public enum VariantMap{
    EARTH,
    HELL;

    public static VariantMap parse(int value){
        return switch (value){
            case 0 -> VariantMap.EARTH;
            case 1 -> VariantMap.HELL;
            default -> null;
        };
    }
}
