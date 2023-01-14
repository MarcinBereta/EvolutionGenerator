package Configuration.Types;


public enum MapTypes {  // raz Panowie mają liczbę pojedynczą, a raz mnogą
    EARTH,
    HELL;

    public static MapTypes parse(int value) {
        return switch (value) {
            case 0 -> MapTypes.EARTH;
            case 1 -> MapTypes.HELL;
            default -> null;
        };
    }
}
