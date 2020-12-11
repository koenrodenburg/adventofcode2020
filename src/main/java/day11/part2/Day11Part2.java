package day11.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static day11.part2.Seat.FLOOR;
import static day11.part2.Seat.OCCUPIED;

public class Day11Part2 {
    private static final String INPUT_PATH = "src/main/resources/day11/input.txt";

    public static void main(String[] args) throws IOException {
        Layout layout = readInput();

        boolean hasChanged = true;
        while (hasChanged) {
            Layout newLayout = iterate(layout);
            hasChanged = !newLayout.equals(layout);
            layout = newLayout;
        }

        int occupiedSeatCount = layout.occupiedSeatCount();

        System.out.println(occupiedSeatCount);
    }

    private static Layout iterate(Layout currentLayout) {
        Layout newLayout = new Layout(currentLayout.getLength(), currentLayout.getWidth());

        for (int y = 0; y < currentLayout.getLength(); y++) {
            for (int x = 0; x < currentLayout.getWidth(); x++) {
                Seat currentSeat = currentLayout.getSeat(y, x);
                if (currentSeat.equals(Seat.EMPTY) && hasNoVisibleSeats(OCCUPIED, y, x, currentLayout)) {
                    newLayout.setSeat(OCCUPIED, y, x);
                } else if (currentSeat.equals(OCCUPIED) && hasAtLeastVisibleSeats(5, OCCUPIED, y, x, currentLayout)) {
                    newLayout.setSeat(Seat.EMPTY, y, x);
                } else {
                    newLayout.setSeat(currentSeat, y, x);
                }
            }
        }

        return newLayout;
    }

    private static boolean hasNoVisibleSeats(Seat ofType, int y, int x, Layout layout) {
        List<Seat> neighbors = getVisibleSeats(y, x, layout);

        return !neighbors.contains(ofType);
    }

    private static boolean hasAtLeastVisibleSeats(int number, Seat ofType, int y, int x, Layout layout) {
        List<Seat> neighbors = getVisibleSeats(y, x, layout);

        long count = neighbors.stream()
                .filter(ofType::equals)
                .count();

        return count >= number;
    }

    private static List<Seat> getVisibleSeats(int y, int x, Layout layout) {
        List<Seat> visibleSeats = new ArrayList<>();

        visibleSeats.add(getFirstVisibleSeat(y, x, -1, -1, layout));
        visibleSeats.add(getFirstVisibleSeat(y, x, -1, 0, layout));
        visibleSeats.add(getFirstVisibleSeat(y, x, -1, 1, layout));
        visibleSeats.add(getFirstVisibleSeat(y, x, 0, -1, layout));
        visibleSeats.add(getFirstVisibleSeat(y, x, 0, 1, layout));
        visibleSeats.add(getFirstVisibleSeat(y, x, +1, -1, layout));
        visibleSeats.add(getFirstVisibleSeat(y, x, +1, 0, layout));
        visibleSeats.add(getFirstVisibleSeat(y, x, +1, 1, layout));

        return visibleSeats;
    }

    private static Seat getFirstVisibleSeat(int y, int x, int yChange, int xChange, Layout layout) {
        int currentY = y + yChange;
        int currentX = x + xChange;
        Seat currentSeat = layout.getSeat(currentY, currentX);
        while (FLOOR.equals(currentSeat)) {
            currentY += yChange;
            currentX += xChange;
            currentSeat = layout.getSeat(currentY, currentX);
        }
        return currentSeat;
    }

    private static Layout readInput() throws IOException {
        List<String> lines = Files.readAllLines(Path.of(INPUT_PATH));
        Layout layout = new Layout(lines.size(), lines.get(0).length());
        for (int y = 0; y < lines.size(); y++) {
            char[] line = lines.get(y).toCharArray();
            for (int x = 0; x < line.length; x++) {
                char seatChar = line[x];
                layout.setSeat(Seat.valueOf(seatChar), y, x);
            }
        }
        return layout;
    }
}
