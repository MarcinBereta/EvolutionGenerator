package mapElements;

public interface IGrass {

    void addGrass();

    void removeGrass(Grass grass);

    Grass grassAtPosition(Vector2d position);
}