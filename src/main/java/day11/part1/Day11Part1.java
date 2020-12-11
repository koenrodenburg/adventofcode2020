package day11.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static day11.part1.Seat.EMPTY;
import static day11.part1.Seat.OCCUPIED;

public class Day11Part1 {
    private static final String INPUT_PATH = "src/main/resources/day11/input.txt";

    public static void main(String[] args) throws IOException {
        Layout layout = readInput();

        boolean hasChanged = true;
        while (hasChanged) {
            System.out.println("Iterate");

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
                if (currentSeat.equals(EMPTY) && hasNoNeighbors(OCCUPIED, y, x, currentLayout)) {
                    newLayout.setSeat(OCCUPIED, y, x);
                } else if (currentSeat.equals(OCCUPIED) && hasAtLeastNeighbors(4, OCCUPIED, y, x, currentLayout)) {
                    newLayout.setSeat(EMPTY, y, x);
                } else {
                    newLayout.setSeat(currentSeat, y, x);
                }
            }
        }

        return newLayout;
    }

    private static boolean hasNoNeighbors(Seat ofType, int y, int x, Layout layout) {
        List<Seat> neighbors = getNeighbors(y, x, layout);

        return !neighbors.contains(ofType);
    }

    private static boolean hasAtLeastNeighbors(int number, Seat ofType, int y, int x, Layout layout) {
        List<Seat> neighbors = getNeighbors(y, x, layout);

        long count = neighbors.stream()
                .filter(ofType::equals)
                .count();

        return count >= number;
    }

    private static List<Seat> getNeighbors(int y, int x, Layout layout) {
        List<Seat> neighbors = new ArrayList<>();

        neighbors.add(layout.getSeat(y - 1, x - 1));
        neighbors.add(layout.getSeat(y - 1, x));
        neighbors.add(layout.getSeat(y - 1, x + 1));
        neighbors.add(layout.getSeat(y, x - 1));
        neighbors.add(layout.getSeat(y, x + 1));
        neighbors.add(layout.getSeat(y + 1, x - 1));
        neighbors.add(layout.getSeat(y + 1, x));
        neighbors.add(layout.getSeat(y + 1, x + 1));
        return neighbors;
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
