public class Ship {

    // Variabler
    private String type;
    private int length;
    private int hits = 0;
    private String shipIdentity;
    private boolean sunk = false;

    // Konstruktor
    public Ship(String type, int length, String shipIdentity) {
        this.type = type;
        this.length = length;
        this.shipIdentity = shipIdentity;
    }

    // Getters & Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getShipIdentity() {
        return shipIdentity;
    }

    public void setShipIdentity(String shipIdentity) {
        this.shipIdentity = shipIdentity;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public boolean isSunk() {
        return sunk;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

}