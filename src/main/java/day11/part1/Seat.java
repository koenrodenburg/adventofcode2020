package day11.part1;

public enum Seat {
    FLOOR,
    EMPTY,
    OCCUPIED;

    public static Seat valueOf(char character) {
        if (character == '.') {
            return FLOOR;
        } else if (character == 'L') {
            return EMPTY;
        } else {
            return OCCUPIED;
        }
    }
}
