package day11.part1;

import lombok.EqualsAndHashCode;

import static day11.part1.Seat.OCCUPIED;

@EqualsAndHashCode
public class Layout {
    private final Seat[][] layout;

    public Layout(int length, int width) {
        layout = new Seat[length][width];
    }

    public Seat getSeat(int y, int x) {
        if (y >= 0 && x >= 0 && y < getLength() && x < getWidth()) {
            return layout[y][x];
        } else {
            return null;
        }
    }

    public int occupiedSeatCount() {
        int count = 0;
        for (int y = 0; y < getLength(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                if (OCCUPIED.equals(getSeat(y, x))) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getLength() {
        return layout.length;
    }

    public int getWidth() {
        return layout[0].length;
    }

    public void setSeat(Seat seat, int y, int x) {
        layout[y][x] = seat;
    }
}
