package mapElements;

public class FieldHistory {
    int deathCount = 0;  // modyfikator dostępu
    Vector2d position;  // modyfikator dostępu

    public FieldHistory(Vector2d position, int deathCount) {
        this.position = position;
        this.deathCount = deathCount;
    }

    public void increaseDeathCount() {
        this.deathCount++;
    }

    public int getDeathCount() {
        return this.deathCount;
    }

    public Vector2d getPosition() {
        return this.position;
    }
}
