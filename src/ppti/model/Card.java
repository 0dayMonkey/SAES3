package ppti.model;


public class Card {
    private char color;
    private int value;
    private double buoy;

    public Card(char color, int value, double buoy) {
        this.color = color;
        this.value = value;
        this.buoy = buoy;
    }

    public char getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public double getBuoy() {
        return buoy;
    }

    @Override
    public String toString() {
        return String.format("%c%02d", color, value);
    }
}
